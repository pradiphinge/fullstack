
package com.mpscstarter.backend.service;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mpscstarter.backend.persistence.domain.backend.Plan;
import com.mpscstarter.backend.persistence.domain.backend.Role;
import com.mpscstarter.backend.persistence.domain.backend.User;
import com.mpscstarter.backend.persistence.domain.backend.UserRole;
import com.mpscstarter.backend.persistence.repositories.PlanRepository;
import com.mpscstarter.backend.persistence.repositories.RoleRepository;
import com.mpscstarter.backend.persistence.repositories.UserRepository;
import com.mpscstarter.backend.persistence.repositories.UserRoleRepository;
import com.mpscstarter.enums.PlansEnum;
import com.mpscstarter.enums.RolesEnum;
import com.mpscstarter.utils.UserUtils;

/**
 * Created by @author Pradipkumar Hinge on July6,2019 
 *
 */
@Service
@Transactional(readOnly=true)
public class UserService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordCoder;
	
	/** The application logger*/
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Transactional 
	public User createUser(User user,PlansEnum plansEnum, Set<UserRole> userRoles) {
		
		String encryptedPassword =passwordCoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		
		Plan plan = new Plan(plansEnum);
		if(!planRepository.existsById(plansEnum.getId())) {
			plan= planRepository.save(plan);
		}
		
		user.setPlan(plan);
		
		
		for(UserRole ur:userRoles) {
			if(!roleRepository.existsById(ur.getRole().getId()))
				 roleRepository.save(ur.getRole());
		} 
		
		user.getUserRoles().addAll(userRoles);
		user = userRepository.save(user);
		
		for(UserRole ur:userRoles) {
			 ur.setUser(user);
			 Optional<Role> role = roleRepository.findById(ur.getRole().getId());
			 Role newRole =role.orElse(null) ;
			 if(newRole!=null) {
				 ur.setRole(newRole);
				 userRoleRepository.save(ur);
			 }
			 
		}
		return user;
	}
	
	@Transactional 
	public void deleteUser(Long id) {
	//User basicUser = createDummyUser();
	userRepository.deleteById(id);
	}
	private User createDummyUser() {
		Plan basicPlan = new Plan(PlansEnum.BASIC);
		basicPlan = planRepository.save(basicPlan);

		User basicUser = UserUtils.createBasicUser("basicUser","hingepradeepster@gmail.com");
		basicUser.setPlan(basicPlan);
		
		Role basicRole = new Role(RolesEnum.BASIC);
		basicRole=roleRepository.save(basicRole);
		
		Set<UserRole> userRoles = new HashSet<>();
		UserRole userRole = new UserRole(basicUser, basicRole) ;
		userRoles.add(userRole);
		
		basicUser.getUserRoles().addAll(userRoles);
		
		basicUser = userRepository.save(basicUser);
		userRole.setUser(basicUser);
		userRole.setRole(basicRole);
		userRole=userRoleRepository.save(userRole);
		
		return basicUser;
	}
	
	@Transactional
	public void updateUserPassword(long userId,String password) {
		password = passwordCoder.encode(password);
		userRepository.updateUserPassword(userId, password);
		LOG.debug("User id {} password updated ", userId);
		
	}
	
	/**
	 * returns a user by username if found or null
	 * @param username
	 * @return user or null if not found 
	 * 
	 */
	public User findByUserName(String username) {
		return userRepository.findByUsername(username);
	}
	/**
	 * returns a user by email if found or null
	 * @param email
	 * @return user or null if not found 
	 * 
	 */
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
}


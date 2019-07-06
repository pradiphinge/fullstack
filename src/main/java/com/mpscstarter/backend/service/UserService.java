
package com.mpscstarter.backend.service;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mpscstarter.backend.persistence.domain.backend.Plan;
import com.mpscstarter.backend.persistence.domain.backend.User;
import com.mpscstarter.backend.persistence.domain.backend.UserRole;
import com.mpscstarter.backend.persistence.repositories.PlanRepository;
import com.mpscstarter.backend.persistence.repositories.RoleRepository;
import com.mpscstarter.backend.persistence.repositories.UserRepository;
import com.mpscstarter.enums.PlansEnum;
import com.mpscstarter.enums.RolesEnum;

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

	@Transactional 
	public User createUser(User user,PlansEnum plansEnum, Set<UserRole> userRoles) {
			
		Plan plan = new Plan(plansEnum);
		if(!planRepository.existsById(plansEnum.getId())) {
			plan= planRepository.save(plan);
		}
		
		user.setPlan(plan);
		
		for(UserRole ur:userRoles) {
			roleRepository.save(ur.getRole());
		} 
		
		user.getUserRoles().addAll(userRoles);
		user = userRepository.save(user);
		
		return user;
	}
}

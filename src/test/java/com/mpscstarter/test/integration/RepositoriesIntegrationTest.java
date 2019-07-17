package com.mpscstarter.test.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RepositoriesIntegrationTest {

	@Autowired
	private PlanRepository planRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserRoleRepository userRoleRepository; 
	
	
	@Before
	public void init() {
		Assert.assertNotNull(planRepository);
		Assert.assertNotNull(roleRepository);
		Assert.assertNotNull(userRepository);
	}
	@Test
	public void testCreateNewPlan() throws Exception{
		Plan basicPlan = createBasicPlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);
		Optional<Plan> retriedPlan = planRepository.findById(PlansEnum.BASIC.getId());
		Assert.assertNotNull(retriedPlan);
	}
	@Test
	public void testCreateNewRole()throws Exception{
		Role role = createBasicRole(RolesEnum.BASIC);
		roleRepository.save(role);
		Optional<Role> retrievedRole = roleRepository.findById(RolesEnum.BASIC.getId());
		Assert.assertNotNull(retrievedRole);
	}
	
	@Test
	public void testNewUser() throws Exception{
	
		User basicUser = createUser("basicUser2","b2@gmail.com");
		
		
		Optional<User> newlyCreatedUser=userRepository.findById(basicUser.getId());
		basicUser = newlyCreatedUser.orElse(null);
		
		Assert.assertNotNull(basicUser);
		Assert.assertTrue(basicUser.getId()!=0);
		Assert.assertNotNull(basicUser.getPlan());
		Assert.assertNotNull(basicUser.getPlan().getId());
		
		Set<UserRole> newRoles = basicUser.getUserRoles();
		for (UserRole userRole2 : newRoles) {
			Assert.assertNotNull(userRole2.getRole());
			Assert.assertNotNull(userRole2.getRole().getId());
		}
		
		
	}
	@Test
	public void testDeleteUser() {
		
		User basicUser = createUser("test","test@gmail.com");
		userRepository.deleteById(basicUser.getId());
	}
	
	
	@Test
	public void testGetUserByEmail() throws Exception {
		String email = "testFindByEmail@mpscstarter.com";
		User user = createUser("testFindByEmail", email);
		
		User newlyFoundUser = userRepository.findByEmail(user.getEmail());
		Assert.assertNotNull(newlyFoundUser);
		Assert.assertNotNull(newlyFoundUser.getId());
	}
	
	@Test
	public void testPasswordUpdate() throws Exception{
		
		String email = "testPasswordUpdate@mpscstarter.com";
		User user = createUser("testPasswordUpdate", email);
		
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());
		
		String newPassword = UUID.randomUUID().toString();
		
		userRepository.updateUserPassword(user.getId(), newPassword);
		
		Optional<User> nuser = userRepository.findById(user.getId());
		user = nuser.orElse(null);
		
		Assert.assertEquals(newPassword, user.getPassword());
		
		
	}
	
	private Plan createBasicPlan(PlansEnum plansEnum) {
		
		return new Plan(plansEnum);
	}
	
	private Role createBasicRole(RolesEnum rolesEnum) {
		
		return new Role(rolesEnum);	
	}
	
	private User createUser(String username,String email) {
		Plan basicPlan = new Plan(PlansEnum.BASIC);
		basicPlan = planRepository.save(basicPlan);

		User basicUser = UserUtils.createBasicUser(username,email);
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

	
}

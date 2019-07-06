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
import com.mpscstarter.enums.PlansEnum;
import com.mpscstarter.enums.RolesEnum;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
		Plan basicPlan = createBasicPlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);
		
		User basicUser = createBasicUser();
		basicUser.setPlan(basicPlan);
		
		Role basicRole = createBasicRole(RolesEnum.BASIC);
		Set<UserRole> userRoles= new HashSet<>();
		
		UserRole userRole = new UserRole(basicUser,basicRole);
		userRoles.add(userRole);
		
		basicUser.getUserRoles().addAll(userRoles);    /// very important. Take care with collection objects
		
		for (UserRole ur : userRoles) {
			roleRepository.save(ur.getRole());
		}

		basicUser=userRepository.save(basicUser);
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
	
	private Plan createBasicPlan(PlansEnum plansEnum) {
		
		return new Plan(plansEnum);
	}
	
	private Role createBasicRole(RolesEnum rolesEnum) {
		
		return new Role(rolesEnum);	
	}
	
	private User createBasicUser() {
		User user = new User();
		user.setUsername("username");
		user.setPassword("password");
		user.setEmail("me@example.com");
		user.setEnabled(true);
		user.setCountry("India");
		user.setDescription("Dummy user");
		user.setFirstname("firstname");
		user.setLastname("lastname");
		user.setPhonenumber("1111111");
		user.setProfileImageUrl("http://blabla.com/basicuser");
		return user;
	}
	
}

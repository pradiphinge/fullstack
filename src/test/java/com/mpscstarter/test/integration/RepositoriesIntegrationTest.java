package com.mpscstarter.test.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.mpscstarter.MpscstarterApplication;
import com.mpscstarter.backend.persistence.domain.backend.Plan;
import com.mpscstarter.backend.persistence.domain.backend.Role;
import com.mpscstarter.backend.persistence.domain.backend.User;
import com.mpscstarter.backend.persistence.domain.backend.UserRole;
import com.mpscstarter.backend.persistence.repositories.PlanRepository;
import com.mpscstarter.backend.persistence.repositories.RoleRepository;
import com.mpscstarter.backend.persistence.repositories.UserRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.HashSet;
import java.util.Iterator;
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
	
	
	private static final int BASIC_PLAN_ID=1;
	private static final int BASIC_ROLE_ID =1;
	
	@Before
	public void init() {
		Assert.assertNotNull(planRepository);
		Assert.assertNotNull(roleRepository);
		Assert.assertNotNull(userRepository);
	}
	@Test
	public void testCreateNewPlan() throws Exception{
		Plan basicPlan = createBasicPlan();
		planRepository.save(basicPlan);
		Optional<Plan> retriedPlan = planRepository.findById(BASIC_PLAN_ID);
		Assert.assertNotNull(retriedPlan);
	}
	@Test
	public void testCreateNewRole()throws Exception{
		Role role = createBasicRole();
		roleRepository.save(role);
		Optional<Role> retrievedRole = roleRepository.findById(BASIC_ROLE_ID);
		Assert.assertNotNull(retrievedRole);
	}
	
	@Test
	public void testNewUser() throws Exception{
		Plan basicPlan = createBasicPlan();
		planRepository.save(basicPlan);
		
		User basicUser = createBasicUser();
		basicUser.setPlan(basicPlan);
		
		Role basicRole = createBasicRole();
		Set<UserRole> userRoles= new HashSet<>();
		
		UserRole userRole = new UserRole();
		userRole.setUser(basicUser);
		userRole.setRole(basicRole);
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
	
	private Plan createBasicPlan() {
		Plan plan = new Plan();
		plan.setId(BASIC_PLAN_ID);
		plan.setName("Basic");
		return plan;
	}
	
	private Role createBasicRole() {
		Role role = new Role();
		role.setId(BASIC_ROLE_ID);
		role.setName("ROLE_USER");
		return role;
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

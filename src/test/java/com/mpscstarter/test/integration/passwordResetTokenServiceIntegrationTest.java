package com.mpscstarter.test.integration;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mpscstarter.backend.persistence.domain.backend.PasswordResetToken;
import com.mpscstarter.backend.persistence.domain.backend.Plan;
import com.mpscstarter.backend.persistence.domain.backend.Role;
import com.mpscstarter.backend.persistence.domain.backend.User;
import com.mpscstarter.backend.persistence.domain.backend.UserRole;
import com.mpscstarter.backend.persistence.repositories.PlanRepository;
import com.mpscstarter.backend.persistence.repositories.RoleRepository;
import com.mpscstarter.backend.persistence.repositories.UserRepository;
import com.mpscstarter.backend.persistence.repositories.UserRoleRepository;
import com.mpscstarter.backend.service.PasswordResetTokenService;
import com.mpscstarter.enums.PlansEnum;
import com.mpscstarter.enums.RolesEnum;
import com.mpscstarter.utils.UserUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class passwordResetTokenServiceIntegrationTest {

	@Autowired 
	private PasswordResetTokenService passwordResetTokenService;
	
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserRoleRepository userRoleRepository; 
	@Autowired
	private PlanRepository planRepository;
	
	
	@Rule public TestName testName = new TestName();
	
	@Test
	public void testCreateNewTokenForUserEmail() throws Exception{
		String email =testName.getMethodName().concat("@mpscstarter.com");
		User user = createUser(testName.getMethodName(),email);
		
		PasswordResetToken passwordResetToken = 
				passwordResetTokenService.createPasswordResetTokenForEmail(user.getEmail());
		
		Assert.assertNotNull(passwordResetToken);
		Assert.assertNotNull(passwordResetToken.getToken());
		
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

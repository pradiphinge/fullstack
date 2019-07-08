package com.mpscstarter.test.integration;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mpscstarter.backend.persistence.domain.backend.Role;
import com.mpscstarter.backend.persistence.domain.backend.User;
import com.mpscstarter.backend.persistence.domain.backend.UserRole;
import com.mpscstarter.backend.service.UserService;
import com.mpscstarter.enums.PlansEnum;
import com.mpscstarter.enums.RolesEnum;
import com.mpscstarter.utils.UserUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserServiceIntegrationTest {

	@Autowired
	private UserService userService;
	
	@Test
	public void testCreateNewUser() throws Exception{
		Set<UserRole> userRoles= new HashSet<>();
		User basicUser =UserUtils.createBasicUser("basicUser3","gg@gmail.com");
		userRoles.add(new UserRole(basicUser, new Role(RolesEnum.BASIC)));
		
		User user = userService.createUser(basicUser, PlansEnum.BASIC, userRoles);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());
	}
}

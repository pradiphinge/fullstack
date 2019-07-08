package com.mpscstarter.utils;

import com.mpscstarter.backend.persistence.domain.backend.User;

/**
 * Utility method to create user
 * Created by @author Pradipkumar Hinge on July 6, 2019
 *
 */
public class UserUtils {
	
	/**
	 * Non instantiable
	 * */
	private UserUtils(){
		throw new AssertionError("non instantiable");
	}
	
	/**
	 * creates a user with basic attributes set
	 * @return a User entity
	 * @param username The username
	 * @param email The email
	 * */
	public static User createBasicUser(String username, String email) {
		User user = new User();
		user.setUsername(username);
		user.setPassword("password");
		user.setEmail(email);
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

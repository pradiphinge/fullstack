package com.mpscstarter.utils;

import javax.servlet.http.HttpServletRequest;

import com.mpscstarter.backend.persistence.domain.backend.User;
import com.mpscstarter.web.controllers.ForgotMyPasswordController;
import com.mpscstarter.web.domain.frontend.BasicAccountPayload;

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

	public static String createPasswordResetUrl(HttpServletRequest request, long userId, String token) {

	String passwordResetUrl =request.getScheme()+"://"
							+request.getServerName()+":"
							+request.getServerPort()
							+request.getContextPath()
							+ForgotMyPasswordController.CHANGE_PASSWORD_PATH
							+"?id=" +userId
							+"&token="+token;
			
	return passwordResetUrl;		
	}

	public static <T extends BasicAccountPayload>User fromWebUserToDomainUser(T frontEndPayload) {
		User user = new User();
		user.setEmail(frontEndPayload.getEmail());
		user.setUsername(frontEndPayload.getUsername());
		user.setPassword(frontEndPayload.getPassword());
		user.setFirstname(frontEndPayload.getFirstName());
		user.setLastname(frontEndPayload.getLastName());
		user.setCountry(frontEndPayload.getCountry());
		user.setPhonenumber(frontEndPayload.getPhoneNumber());
		user.setEnabled(true);
		user.setDescription(frontEndPayload.getDescription());
				
		return user;
	}
}

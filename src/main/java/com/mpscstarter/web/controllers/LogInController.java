
package com.mpscstarter.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This is logIn Controller
 * @author Pradipkumar Hinge
 *Created on 5 July 2019

 */
@Controller
public class LogInController {

	/*Constant view name*/
	public static final String LOGIN_VIEW_NAME="user/login";
	
	@RequestMapping(value="/login" ,method=RequestMethod.GET)
	public String showLogIn() {
		return LOGIN_VIEW_NAME;
	}
	
}

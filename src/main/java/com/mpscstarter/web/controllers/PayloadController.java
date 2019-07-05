
package com.mpscstarter.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This is payload controller to access premium lectures
 * @author Pradipkumar Hinge
 *Created on 5 July 2019
 */
@Controller
public class PayloadController {

	public static final String PAYLOAD_VIEW_NAME= "payload/payload";
	
	@RequestMapping(value="/payload", method=RequestMethod.GET)
	public String payloadAccess() {
		
		return PAYLOAD_VIEW_NAME;
	}
	
	
}

package com.mpscstarter.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mpscstarter.backend.service.EmailService;
import com.mpscstarter.web.domain.frontend.FeedbackPojo;


/** The Contact Us Controller
 * @since 4 July 2019 
 * @author Pradipkumar Hinge
 * */

@Controller
public class ContactController {

	/** The application logger*/
	
	private static final Logger LOG = LoggerFactory.getLogger(ContactController.class);

	/** The key which identifies the feedback payload in the model */
	public static final String FEEDBACK_MODEL_KEY = "feedback";
	
	/** The Contact Us view name */
	public static final String CONTACT_US_VIEW_NAME = "contact/contact";
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(value ="/contact", method=RequestMethod.GET)
	public String contactGet(ModelMap model) {
		FeedbackPojo feedbackPojo = new FeedbackPojo();
		model.addAttribute(ContactController.FEEDBACK_MODEL_KEY, feedbackPojo);
		return ContactController.CONTACT_US_VIEW_NAME;
	}
	
	@RequestMapping(value ="/contact", method=RequestMethod.POST)
	public String contactPost(@ModelAttribute(FEEDBACK_MODEL_KEY) FeedbackPojo feedback) {
		LOG.info("FeedbackPojo content: {}", feedback);
		System.out.println(feedback);
		emailService.sendFeedbackEmail(feedback);
		return ContactController.CONTACT_US_VIEW_NAME;
	}
	
}

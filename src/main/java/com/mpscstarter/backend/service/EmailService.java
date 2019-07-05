package com.mpscstarter.backend.service;

import org.springframework.mail.SimpleMailMessage;

import com.mpscstarter.web.domain.frontend.FeedbackPojo;

/**
 * Contract for email service
 * @author Pradipkumar Hinge
 *  created on 44 July 2019
 */

public interface EmailService {

	/**
	 * sends an email with the content in the Feedback Pojo
	 * @param feedbackPojo the feedbackPojo
	 * 
	 * */
	public void sendFeedbackEmail(FeedbackPojo feedbackPojo);
	/**
	 * sends an email with the content of the Simple Mail Message object
	 * @param message The object containing the email content
	 * */
	public void sendGenericEmailMessage(SimpleMailMessage message); 
}

package com.mpscstarter.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.mpscstarter.web.domain.frontend.FeedbackPojo;

/**
 * @author Pradipkumar Hinge
 * created on 4 July 2019
 * 
 */
public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.to.address}")
	private String defaultToAddress;

	@Override
	public void sendFeedbackEmail(FeedbackPojo feedbackPojo) {
		sendGenericEmailMessage(prepareSimpleMailMessageFromFeedbackPojo(feedbackPojo));
	}

	/**
	 * Prepares Simple Mail Message from feedbackPojo
	 * @param feedbackPojo the feedbackPojo object
	 * **/
	protected SimpleMailMessage prepareSimpleMailMessageFromFeedbackPojo(FeedbackPojo feedbackPojo) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(defaultToAddress);
		message.setFrom(feedbackPojo.getEmail());
		message.setSubject("[MPSC Starter]:Feedback Received from "+feedbackPojo.getFirstName()+" "+feedbackPojo.getLastName()+" !");
		message.setText(feedbackPojo.getFeedback());
		return message;
	} 
	
}

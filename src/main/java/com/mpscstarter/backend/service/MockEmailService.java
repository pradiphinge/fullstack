
package com.mpscstarter.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author Pradipkumar Hinge
 * Created on 4 July 2019
 *
 */
public class MockEmailService extends AbstractEmailService {

	/** The application logger*/
	
	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void sendGenericEmailMessage(SimpleMailMessage message) {
		LOG.debug("simulating email service....");
		LOG.info(message.toString());
		LOG.debug("email sent.....");
	}

}

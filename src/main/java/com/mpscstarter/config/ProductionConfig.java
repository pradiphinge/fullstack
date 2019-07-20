package com.mpscstarter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.mpscstarter.backend.service.EmailService;
import com.mpscstarter.backend.service.SmtpEmailService;

@Configuration
@Profile("prod")
@PropertySource("file:///${user.home}/.mpscstarter/application-dev.properties")
public class ProductionConfig {

	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
		
	}
}

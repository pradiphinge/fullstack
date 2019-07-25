package com.mpscstarter.config;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.mpscstarter.backend.service.EmailService;
import com.mpscstarter.backend.service.MockEmailService;

@Configuration
@Profile("dev")
@PropertySource("file:///${user.home}/.mpscstarter/application-dev.properties")
public class DevelopmentConfig {

	@Value("${stripe.test.private.key}")
	private String stripeDevKey;
	
	@Bean
	public String stripeKey() {
		return stripeDevKey;
	}
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}

	@Bean
	public ServletRegistrationBean<WebServlet> h2ConsoleServletRegistration() {
			
		ServletRegistrationBean<WebServlet> bean = new ServletRegistrationBean<WebServlet>(new WebServlet());
		bean.addUrlMappings("/console/*");
		return bean;
		}
	}
	

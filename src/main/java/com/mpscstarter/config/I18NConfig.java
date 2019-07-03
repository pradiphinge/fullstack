package com.mpscstarter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * This configuration class helps us achieve InternationalisatioN
 *                    				           <------18------->
 * @author Pradipkumar Hinge
 * @version 1.0.0  
 * @since 3 July 2019    
 */
@Configuration
public class I18NConfig {
	@Bean 
	public ReloadableResourceBundleMessageSource messageSource() {
		
		ReloadableResourceBundleMessageSource resourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("classpath:i18n/messages");
		//checks for new messages every 30 minutes
		resourceBundleMessageSource.setCacheSeconds(1800);
		return resourceBundleMessageSource;
	}

}

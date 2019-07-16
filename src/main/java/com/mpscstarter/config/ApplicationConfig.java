
package com.mpscstarter.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Application Wide Configuration for better system performance
 * @author Pradipkumar Hinge
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.mpscstarter.backend.persistence.repositories")
@EntityScan(basePackages = "com.mpscstarter.backend.persistence.domain.backend")
@EnableTransactionManagement
@PropertySource("file:///${user.home}/.mpscstarter/application-common.properties")
public class ApplicationConfig {

}

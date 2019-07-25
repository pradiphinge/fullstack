
package com.mpscstarter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

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
@PropertySource("file:///${user.home}/.mpscstarter/stripe.properties")
public class ApplicationConfig {

	@Value("${aws.s3.profile}")
	private String awsProfileName;
	
	@Value("${aws.s3.access.key}")
	private String awsAccessKey;
	
	@Value("${aws.s3.secret.access.key}")
	private String awsSecretAccess;
	
	@Bean
	public AmazonS3 s3Client() {
		
		BasicAWSCredentials credentials = new BasicAWSCredentials(awsAccessKey, awsSecretAccess);
		
		AmazonS3 s3Client = AmazonS3Client.builder().withRegion(Regions.AP_SOUTH_1)
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
		return s3Client;
		
	}
}

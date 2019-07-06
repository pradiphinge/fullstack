package com.mpscstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.mpscstarter.backend.persistence.repositories")
public class MpscstarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MpscstarterApplication.class, args);
	}

}

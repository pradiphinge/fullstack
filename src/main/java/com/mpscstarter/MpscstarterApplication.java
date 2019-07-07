package com.mpscstarter;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import com.mpscstarter.backend.persistence.domain.backend.Role;
import com.mpscstarter.backend.persistence.domain.backend.User;
import com.mpscstarter.backend.persistence.domain.backend.UserRole;
import com.mpscstarter.backend.service.UserService;
import com.mpscstarter.enums.PlansEnum;
import com.mpscstarter.enums.RolesEnum;
import com.mpscstarter.utils.UserUtils;

@SpringBootApplication
public class MpscstarterApplication implements CommandLineRunner{

	/** The application logger*/
	
	private static final Logger LOG = LoggerFactory.getLogger(MpscstarterApplication.class);

	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(MpscstarterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Set<UserRole> userRoles= new HashSet<>();
		User basicUser =UserUtils.createBasicUser();
		userRoles.add(new UserRole(basicUser, new Role(RolesEnum.PRO)));
		LOG.debug("Creating user wit username{}",basicUser.getUsername());
		LOG.info("Creating user wit username{}",basicUser.getUsername());
		User user = userService.createUser(basicUser, PlansEnum.PRO, userRoles);
		LOG.info("user {} created",user.getUsername());
		//userService.deleteUser((long) 1);
	}

}

package com.mpscstarter;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import com.mpscstarter.backend.persistence.domain.backend.Role;
import com.mpscstarter.backend.persistence.domain.backend.User;
import com.mpscstarter.backend.persistence.domain.backend.UserRole;
import com.mpscstarter.backend.service.PlanService;
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
	
	@Autowired
	private PlanService planService;
	
	@Value("${webmaster.username}")
	private String webmasterUsername;

	@Value("${webmaster.password}")
	private String webmasterPassword;
	
	@Value("${webmaster.email}")
	private String webmasterEmail;
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(MpscstarterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		LOG.info("Creating Basic and Pro plan in the database...");
		planService.createPlan(PlansEnum.BASIC.getId());
		planService.createPlan(PlansEnum.PRO.getId());
		
		Set<UserRole> userRoles= new HashSet<>();
		User basicUser =UserUtils.createBasicUser(webmasterUsername,webmasterEmail);
		userRoles.add(new UserRole(basicUser, new Role(RolesEnum.ADMIN)));
		LOG.debug("Creating user wit username{}",basicUser.getUsername());
		LOG.info("Creating user wit username{}",basicUser.getUsername());
		
		User user = userService.createUser(basicUser, PlansEnum.PRO, userRoles);
		LOG.info("user {} created",user.getUsername());
		//userService.deleteUser((long) 1);
	}

}

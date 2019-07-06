package com.mpscstarter.config;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * Security configuration controller
 * @author Pradipkumar Hinge
 * created on 5 July 2019
 * 
 * */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;
	/**
	 * public urls
	 * */
private static final String[] PUBLIC_MATCHERS= {
		"/webjars/**",
		"/css/**",
		"/js/**",
		"/images/**",
		"/",
		"/about/**",
		"/contact/**",
		"/error/**/*",
		"/console/**"
};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		List<String> activeProfiles = new ArrayList<String>();   // = Arrays.asList(env.getActiveProfiles());
		
		for (String string : env.getActiveProfiles()) {
			activeProfiles.add(string);
		}
		
		if(activeProfiles.contains("dev")) {
			http.csrf().disable();
			http.headers().frameOptions().disable();
		}
		
		http
			.authorizeRequests()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login").defaultSuccessUrl("/payload")
			.failureUrl("/login?error").permitAll()
			.and()
			.logout().permitAll();
	}	

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth
			.inMemoryAuthentication()
			.withUser("user").password("{noop}password")
			.roles("USER");
	}

/*
 * @Bean
    public UserDetailsService userDetailsService() {

        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("password").roles("USER").build());
        manager.createUser(users.username("admin").password("password").roles("USER", "ADMIN").build());
        return manager;

    }

 * 
 * 
 * */
}

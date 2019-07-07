/**
 * 
 */
package com.mpscstarter.backend.persistence.domain.backend;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by @author Pradipkumar Hinge on July 7, 2019
 *
 */
public class Authority implements GrantedAuthority {

	private final String authority; 
	
	
	public Authority(String authority) {
		super();
		this.authority = authority;
	}


	@Override
	public String getAuthority() {
		return authority;
	}

}

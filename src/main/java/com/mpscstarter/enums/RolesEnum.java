
package com.mpscstarter.enums;

/**
 * Roles : Basic, Pro, Admin
 * Created by @author Pradipkumar Hinge on 6 July 2019
 *
 */
public enum RolesEnum {

	BASIC(1,"ROLE_BASIC"),
	PRO(2,"ROLE_PRO"),
	ADMIN(3,"ROLE_ADMIN");
	
	private final int id;
	private final String rolename;
	
	RolesEnum(int id, String rolename){
		this.id=id;
		this.rolename = rolename;
	}

	public int getId() {
		return id;
	}

	public String getRolename() {
		return rolename;
	}
	
	
}

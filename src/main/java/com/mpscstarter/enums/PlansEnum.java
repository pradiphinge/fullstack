
package com.mpscstarter.enums;

/**
 * Basic =1 Pro =2 // Pro id 2 is set in stripe
 * Created by @author Pradipkumar Hinge on 6 July, 2019
 *
 */
public enum PlansEnum {

	BASIC(1,"BASIC"),
	PRO(2,"PRO");
	
	private final int id;
	private final String plansName;
	
	PlansEnum(int id, String plansName){
		this.id=id;
		this.plansName=plansName;
	}

	public int getId() {
		return id;
	}

	public String getPlansName() {
		return plansName;
	}
	
	
}

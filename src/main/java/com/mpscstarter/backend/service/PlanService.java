package com.mpscstarter.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mpscstarter.backend.persistence.domain.backend.Plan;
import com.mpscstarter.backend.persistence.repositories.PlanRepository;
import com.mpscstarter.enums.PlansEnum;

/**
 * 
 * @author Pradipkumar Hinge
 * 
 */
@Service
@Transactional(readOnly = true )
public class PlanService {
	
	@Autowired
	private PlanRepository planRepository;
	
	
	public Plan findPlanById(int planId) {
		return planRepository.findById(planId);
	} 
	
	@Transactional
	public Plan createPlan(int planId) {
		Plan plan =null;
		
		if (planId==1) {
			plan = planRepository.save(new Plan(PlansEnum.BASIC));
		}else if (planId ==2) {
			plan = planRepository.save(new Plan(PlansEnum.PRO));
		}else
			throw new IllegalArgumentException("planId "+planId+" not recognised.");
		
		return plan;
	}
}

package com.IES.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.IES.bindings.PlanForm;

@Service
public class PlanServiceImpl implements PlanService{

	@Override
	public boolean createPlan(PlanForm planForm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<PlanForm> fetchPlans() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanForm getPlanById(Integer planId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String changePlanStatus(Integer planId, String planStatus) {
		// TODO Auto-generated method stub
		return null;
	}

}

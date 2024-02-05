package com.IES.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.IES.bindings.DashboardCards;
import com.IES.bindings.LoginForm;
import com.IES.entities.EligEntity;
import com.IES.entities.UserEntity;
import com.IES.repositories.EligRepo;
import com.IES.repositories.PlanRepository;
import com.IES.repositories.UserRepository;
import com.IES.utils.EmailUtils;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PlanRepository planRepo;
	
	@Autowired
	private EligRepo eligRepo;
	
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String loginUser(LoginForm loginForm) {
		UserEntity userEntity = userRepo.findByEmailAndPswd(loginForm.getEmail(), loginForm.getPswd());
		if(userEntity==null) {
			return "invalid credentials";
		}
		
		if("UNLOCKED".equals(userEntity.getAccountStatus()) && "Y".equals(userEntity.getActiveSw())) {
			return "success@role="+userEntity.getRoleId();
		}else {
			return "account locked/inactive";
		}
	}

	@Override
	public boolean recoverPwd(String email) {
		UserEntity userEntity = userRepo.findByEmail(email);
		if(null==userEntity) {
			return false;
		}else {
			String sub="";
			String body="";
			String to=userEntity.getEmail();
			return emailUtils.sendEmail(to, sub, body);
		}
	}

	@Override
	public DashboardCards fetchDashboardInfo() {

		Long plansCount = planRepo.count();
		List<EligEntity> eligList = eligRepo.findAll();
		Long approvedCount = eligList.stream().filter(e -> e.getPlanStatus().equals("AP")).count();
		Long deniedCount = eligList.stream().filter(e-> e.getPlanStatus().equals("DENIED")).count();
		Double sum = eligList.stream().mapToDouble(e->e.getBenefitAmount()).sum();
		DashboardCards card=new DashboardCards();
		card.setPlansCount(plansCount);
		card.setApprovedCount(approvedCount);
		card.setDeniedCount(deniedCount);
		card.setBenefitAmntGiven(sum);
		return card;
	}

}

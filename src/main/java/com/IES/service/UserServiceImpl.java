package com.IES.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.IES.bindings.DashboardCards;
import com.IES.bindings.LoginForm;
import com.IES.bindings.UserAccForm;
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
			String sub="Recover Pwd";
			String body=readEmailBody("FORGOT_PWD_EMAIL_BODY.txt", userEntity);
			return emailUtils.sendEmail(email, sub, body);
		}
	}

	private String readEmailBody(String fileName, UserEntity userEntity) {
		StringBuilder sb=new StringBuilder();
		try(Stream<String> lines=Files.lines(Paths.get(fileName))) {
			lines.forEach(line->{
				line=line.replace("${FNAME}", userEntity.getFullName());
				line=line.replace("${PWD}", userEntity.getPwd());
				line=line.replace("${EMAIL}", userEntity.getEmail());
				sb.append(line);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
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

	@Override
	public UserAccForm getUserByEmail(String email) {
		UserEntity userEntity = userRepo.findByEmail(email);
		UserAccForm accForm=new UserAccForm();
		BeanUtils.copyProperties(userEntity, accForm);
		return accForm;
	}
	
	

}

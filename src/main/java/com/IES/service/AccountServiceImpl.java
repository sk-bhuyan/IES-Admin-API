package com.IES.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.IES.bindings.UnlockAccForm;
import com.IES.bindings.UserAccForm;
import com.IES.entities.UserEntity;
import com.IES.repositories.UserRepository;
import com.IES.utils.EmailUtils;
import com.IES.utils.PwdUtils;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public boolean createUserAccount(UserAccForm userAccountForm) {
		UserEntity entity=new UserEntity();
		BeanUtils.copyProperties(userAccountForm, entity);
		entity.setPwd(PwdUtils.generateRandomPassword());
		entity.setAccountStatus("LOCKED");
		entity.setActiveSw("Y");
		//send email
		String sub="User Registration";
		String body=readEmailBody("REG_EMAIL_BODY.txt", entity);
		return emailUtils.sendEmail(userAccountForm.getEmail(), sub, body);
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
	public List<UserAccForm> fetchUserAccounts() {
		List<UserEntity> userEntities = userRepo.findAll();
		List<UserAccForm> users=new ArrayList<>();
		for(UserEntity e:userEntities) {
			UserAccForm user=new UserAccForm();
			BeanUtils.copyProperties(e, user);
			users.add(user);
		}
		return users;
	}

	@Override
	public UserAccForm getUserAccountById(Integer accountId) {
		Optional<UserEntity> optional = userRepo.findById(accountId);
		if(optional.isPresent()) {
			UserEntity userEntity = optional.get();
			UserAccForm user=new UserAccForm();
			BeanUtils.copyProperties(userEntity, user);
			return user;
		}
		return null;
	}

	@Override
	public String changeAccountStatus(Integer userId, String status) {
		Integer count = userRepo.updateAccStatus(userId, status);
		if(count>0) {
			return "status changed";
		}
		
		return "failed to change";
	}

	@Override
	public String unlockUserAccount(UnlockAccForm unlockForm) {
		UserEntity entity = userRepo.findByEmail(unlockForm.getEmail());
		entity.setPwd(unlockForm.getConfirmPwd());
		entity.setAccountStatus("UNLOCKED");
		userRepo.save(entity);
		return "Account Unlocked";
	}

}

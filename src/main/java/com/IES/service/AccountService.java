package com.IES.service;

import java.util.List;

import com.IES.bindings.UnlockAccForm;
import com.IES.bindings.UserAccForm;

public interface AccountService {
	
	public boolean createUserAccount(UserAccForm userAccountForm);
	
	public List<UserAccForm> fetchUserAccounts();
	
	public UserAccForm getUserAccountById(Integer accountId);
	
	public String changeAccountStatus(Integer accountId, String status);

	public String unlockUserAccount(UnlockAccForm unlockForm);
}

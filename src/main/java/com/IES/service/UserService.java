package com.IES.service;

import com.IES.bindings.DashboardCards;
import com.IES.bindings.LoginForm;
import com.IES.bindings.UserAccForm;

public interface UserService {

	public String loginUser(LoginForm loginForm);
	
	public boolean recoverPwd(String email);
	
	public DashboardCards fetchDashboardInfo();
	
	public UserAccForm getUserByEmail(String email);
}

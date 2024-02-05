package com.IES.service;

import com.IES.bindings.DashboardCards;
import com.IES.bindings.LoginForm;

public interface UserService {

	public String loginUser(LoginForm loginForm);
	
	public boolean recoverPwd(String email);
	
	public DashboardCards fetchDashboardInfo();
}

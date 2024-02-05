package com.IES.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.IES.bindings.DashboardCards;
import com.IES.bindings.LoginForm;
import com.IES.service.UserService;

@RestController
public class UserRestController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public String login(@RequestBody LoginForm loginForm) {
		String status = userService.loginUser(loginForm);
		if(status.equals("success")) {
			return "redirect:/dashboard";
		}else {
			return status;
		}
	}
	
	@GetMapping("/dashboard")
	public ResponseEntity<DashboardCards> buildDashboard(){
		DashboardCards dashInfo = userService.fetchDashboardInfo();
		return new ResponseEntity<DashboardCards>(dashInfo,HttpStatus.OK);
	}

}

package com.IES.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.IES.bindings.UserAccForm;
import com.IES.service.AccountService;

@RestController
public class AccountRestController {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/user")
	public ResponseEntity<String> createAccount(@RequestBody UserAccForm userAccForm){
		boolean status = accountService.createUserAccount(userAccForm);
		if(status) {
			return new ResponseEntity<String>("account created",HttpStatus.CREATED);	//status code: 202
		}
		return new ResponseEntity<String>("account creation failed", HttpStatus.INTERNAL_SERVER_ERROR);		// status code: 500
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<UserAccForm>> getUsers(){
		List<UserAccForm> fetchUserAccounts = accountService.fetchUserAccounts();
		return new ResponseEntity<>(fetchUserAccounts, HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<UserAccForm> getUser(@PathVariable("userId") Integer uid){
		UserAccForm userAccountById = accountService.getUserAccountById(uid);
		return new ResponseEntity<UserAccForm>(userAccountById,HttpStatus.OK);
	}
	
	@PutMapping("/user/{userid}/{status}")
	public ResponseEntity<List<UserAccForm>> updateUserAcc(@PathVariable("userId") Integer uid, @PathVariable("status") String status){
		accountService.changeAccountStatus(uid, status);
		List<UserAccForm> fetchUserAccounts = accountService.fetchUserAccounts();
		return new ResponseEntity<>(fetchUserAccounts, HttpStatus.OK);
	}

}

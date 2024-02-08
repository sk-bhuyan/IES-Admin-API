package com.IES.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private Logger log=LoggerFactory.getLogger(AccountRestController.class);
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/user")
	public ResponseEntity<String> createAccount(@RequestBody UserAccForm userAccForm){
		log.debug("Account creation process started..");
		boolean status = accountService.createUserAccount(userAccForm);
		log.debug("Account creation process started");
		if(status) {
			log.info("Account created successfully!!");
			return new ResponseEntity<String>("account created",HttpStatus.CREATED);	//status code: 202
		}else {
			log.info("Account creation failed");
			return new ResponseEntity<String>("account creation failed", HttpStatus.INTERNAL_SERVER_ERROR);		// status code: 500
		}
		
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<UserAccForm>> getUsers(){
		log.debug("fetching process started");
		List<UserAccForm> fetchUserAccounts = accountService.fetchUserAccounts();
		log.debug("fetching process completed");
		log.info("user accounts fetched success...");
		return new ResponseEntity<>(fetchUserAccounts, HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<UserAccForm> getUser(@PathVariable("userId") Integer uid){
		log.debug("fetching userId process started");
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

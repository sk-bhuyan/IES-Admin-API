package com.IES.bindings;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserAccForm {

private String fullName;
	
	private String email;
	
	private String pwd;
	
	private Long phno;
	
	private String gender;
	
	private Date dob;
	
	private Long ssn;
	
	private String activeSw;
	
	private String accountStatus;
}

package com.IES.bindings;

import lombok.Data;

@Data
public class UnlockAccForm {

	private String email;
	
	private String newPwd;
	
	private String confirmPwd;
	
	private String tempPwd;
}

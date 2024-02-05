package com.IES.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class PwdUtils {

	public static String generateRandomPassword() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		String pwd = RandomStringUtils.random( 15, characters );
		return pwd;
	}
}

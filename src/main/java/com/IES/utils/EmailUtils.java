package com.IES.utils;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

//import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender javaMailSender;

	public boolean sendEmail(String to, String sub, String body) {
		boolean isSend=false;
		
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			
			helper.setTo(to);
			helper.setSubject(sub);
			helper.setText(body);
			
			javaMailSender.send(mimeMessage);
			
			isSend=true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return isSend;
	}
}

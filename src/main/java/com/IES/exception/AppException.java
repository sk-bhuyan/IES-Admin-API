package com.IES.exception;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AppException {
	private String exCode;
	private String exDesc;
	private LocalDateTime exDate;
}

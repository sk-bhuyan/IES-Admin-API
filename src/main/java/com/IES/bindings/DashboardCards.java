package com.IES.bindings;

import lombok.Data;

@Data
public class DashboardCards {
	private UserAccForm user;
	private Long plansCount;
	private Long approvedCount;
	private Long deniedCount;
	private Double benefitAmntGiven;
}

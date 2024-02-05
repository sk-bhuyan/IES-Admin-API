package com.IES.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "IES_PLANS")
public class PlanEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer planId; 
	
	private String planName;
	
	private Date planStartDate;
	
	private Date planEndDate;
	
	private String planCategory;
	
	private String activeSw;
	
	@ManyToOne
    @JoinColumn(name="userid", nullable=false)
    private UserEntity user;
	
}

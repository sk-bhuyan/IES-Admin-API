package com.IES.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="IES_USERS")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	private String fullName;
	
	private String email;
	
	private String pwd;
	
	private Long phno;
	
	private String gender;
	
	private Date dob;
	
	private Integer roleId;
	
	private Long ssn;
	
	private String activeSw;
	
	private String accountStatus;
	
//	@Column(name="CREATE_DATE")
//	private Date createdDate;
//	
//	@Column(name="UPDATE_DATE")
//	private Date updatedDate;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<PlanEntity> plans;
}

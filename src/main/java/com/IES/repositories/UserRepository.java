package com.IES.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.IES.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	
	//custom query to update account status
	@Query("update UserEntity set accountStatus=:status where userid=:accId")
	public Integer updateAccStatus(Integer userId, String status);

	public UserEntity findByEmail(String email);
	
	public UserEntity findByEmailAndPswd(String email, String pswd);
}

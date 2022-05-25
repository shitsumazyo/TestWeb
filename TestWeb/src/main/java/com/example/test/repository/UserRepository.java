package com.example.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.test.model.UserInfo;


public interface UserRepository extends JpaRepository<UserInfo, Long> {
	
	UserInfo findByUsername(String name);
	
}

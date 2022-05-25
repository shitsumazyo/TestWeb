package com.example.test.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.test.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
	
	Blog findByBlogtitle(String blogtitle);
	
	Blog findByUsername(String blogtitle);
	
	List<Blog> findAllByUsername(String username);
	
	@Transactional
	void deleteByBlogtitle(String Blogtitle);
	
}

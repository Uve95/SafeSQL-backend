package com.backend.SafeSQL.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.SafeSQL.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	public User findByEmail(String email);
}

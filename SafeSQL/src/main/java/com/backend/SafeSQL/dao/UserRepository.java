package com.backend.SafeSQL.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.SafeSQL.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	User findByEmail(String email);
}

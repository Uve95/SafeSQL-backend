package com.backend.SafeSQL.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.SafeSQL.dao.UserRepository;
import com.backend.SafeSQL.model.User;

@Service

public class UserService {

	@Autowired 
	private UserRepository userDAO;
	
	
	public List<User> findAll() {
		return userDAO.findAll();
	}
}

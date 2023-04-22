package com.backend.SafeSQL.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.SafeSQL.model.User;
import com.backend.SafeSQL.service.UserService;


@RestController
@RequestMapping("user")

public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/listUsers")
	public List<User> findAll() {
		return userService.findAll();
	}
}

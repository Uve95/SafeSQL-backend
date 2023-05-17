package com.backend.SafeSQL.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.backend.SafeSQL.model.User;
import com.backend.SafeSQL.service.MailService;
import com.backend.SafeSQL.service.UserService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "*")

public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private MailService mailService;

	@PostMapping("/userSave")
	public User saveUser(@RequestBody User user) {
		return userService.saveUser(user);

	}

	@PostMapping("/login")
	public void loginUser(@RequestBody Map<String, Object> info) throws Exception {

		try {
			JSONObject jso = new JSONObject(info);

			this.userService.loginUser(jso);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}
	}

	@GetMapping("/userList")
	public List<User> findAll() {
		return userService.findAll();
	}

	/*
	 * @GetMapping("/{email}") public User getUser(@PathVariable String email)
	 * throws Exception { return userService.getUser(email);
	 * 
	 * }
	 * 
	 * @PutMapping("/{email}") public User updateUser(@PathVariable String
	 * email, @RequestBody User detallesUser) throws Exception { return
	 * userService.updateUser(email,detallesUser); }
	 * 
	 * @DeleteMapping("/{email}") public void deleteUser(@PathVariable String email)
	 * throws Exception { userService.deleteUser(email); }
	 * 
	 */

	@PostMapping("/forgotPassword")

	public void forgotPassword(@RequestBody Map<String, Object> info) throws Exception {
	//public void forgotPassword() {

		try {
			JSONObject jso = new JSONObject(info);

			mailService.resetPassword(jso);
			//	mailService.sendEmail("vickydaimiel@gmail.com");

		} catch (Exception e) {
			System.out.print(e.getMessage());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}

	

	}

}

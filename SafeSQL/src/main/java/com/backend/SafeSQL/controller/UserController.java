package com.backend.SafeSQL.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import com.backend.SafeSQL.model.Rol;
import com.backend.SafeSQL.model.User;
import com.backend.SafeSQL.model.UserRol;
import com.backend.SafeSQL.service.MailService;
import com.backend.SafeSQL.service.UserService;

@RestController
@RequestMapping("/api/user/")
@CrossOrigin(origins = "*")

public class UserController {


	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	String [] array = new String[72];


	@PostMapping("register")
	public User register(@RequestBody User user) throws Exception {

		user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

		Set<UserRol> userRoles = new HashSet<>();

		Rol rol = new Rol();
		rol.setRolId(2L);
		rol.setRolName("USER");

		UserRol userRol = new UserRol();
		userRol.setUser(user);
		userRol.setRol(rol);

		userRoles.add(userRol);

		return userService.saveUser(user, userRoles);
	}
	
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("update/{token}")
	public ResponseEntity<?> update(@RequestBody User user, @PathVariable("token") String token)
			throws Exception {

		if(user.getPassword() != "")
			user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

		userService.updateUser(user, token);

		return new ResponseEntity<User>(HttpStatus.OK);

	}

	@PostMapping("forgotPassword")
	public void forgotPassword(@RequestBody User user) throws Exception {

		try {

			userService.forgotPassword(user);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}

	}

	@PostMapping("changePassword")
	public void changePassword(@RequestBody User user) throws Exception {
		// public void forgotPassword() {

		try {

			user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
			userService.changePassword(user);
			// mailService.sendEmail("vickydaimiel@gmail.com");

		} catch (Exception e) {
			System.out.print(e.getMessage());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}

	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping("connectBD")
	public void connectBD(@RequestBody String[] info) throws Exception {

		try {

			userService.connectBD(info);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}
	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping("BDName")
	public String BDName(@RequestBody String info) throws Exception {

		try {

			String BDName = userService.BDName(info);

			return BDName;

		} catch (Exception e) {
			System.out.print(e.getMessage());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}

	}
	
	

	@PreAuthorize("hasRole('USER')")
	@PostMapping("checklistConfiguration")
	public String[] checklistConfiguration(@RequestBody String[] info) throws Exception {

		try {

			array = userService.checklistConfig(info);

			return array;

		} catch (Exception e) {
			System.out.print(e.getMessage());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}

	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("checklistNetwork")
	public String[] checklistNetwork(@RequestBody String[] info) throws Exception {

		try {


			array = userService.checklistNetwork(info);

			return array;

		} catch (Exception e) {
			System.out.print(e.getMessage());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}

	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("checklistPermission")
	public String[] checklistPermission(@RequestBody String[] info) throws Exception {

		try {

			array = userService.checklistPermission(info);

			return array;

		} catch (Exception e) {
			System.out.print(e.getMessage());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}

	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("checklistPassword")
	public String[] checklistPassword(@RequestBody String[] info) throws Exception {

		try {


			array = userService.checklistPassword(info);

			return array;

		} catch (Exception e) {
			System.out.print(e.getMessage());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}

	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("checklistSession")
	public String[] checklistSession(@RequestBody String[] info) throws Exception {

		try {


			array = userService.checklistSession(info);

			return array;

		} catch (Exception e) {
			System.out.print(e.getMessage());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}

	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("checklistMaintenance")
	public String[] checklistMaintenance(@RequestBody String[] info) throws Exception {

		try {


			array = userService.checklistMaintenance(info);

			return array;

		} catch (Exception e) {
			System.out.print(e.getMessage());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}

	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("checklistData")
	public String[] checklistData(@RequestBody String[] info) throws Exception {

		try {


			array = userService.checklistData(info);

			return array;

		} catch (Exception e) {
			System.out.print(e.getMessage());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}

	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("checklistRol")
	public String[] checklistRol(@RequestBody String[] info) throws Exception {

		try {

			array = userService.checklistRol(info);

			return array;

		} catch (Exception e) {
			System.out.print(e.getMessage());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}

	}
	
	//@PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
	@GetMapping("actual-token/{email}")
	public String getToken(@PathVariable("email") String email) throws Exception {

		try {

			String token = userService.getToken(email);

			return token;

		} catch (Exception e) {
			System.out.print(e.getMessage());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}

	}
	
	//@PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
	@PostMapping("delete-info")
	public void deleteInfo(@RequestBody String info) throws Exception {

		try {

			userService.deleteInfo(info);


		} catch (Exception e) {
			System.out.print(e.getMessage());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}

	}
	

}

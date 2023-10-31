package com.backend.SafeSQL.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.SafeSQL.model.User;
import com.backend.SafeSQL.service.AdminService;

@RestController
@RequestMapping("/api/admin/")
@CrossOrigin(origins = "*")

public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("list")
	public List<User> findAll() throws Exception {

		return adminService.list();
	}

	@PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
	@GetMapping("details/{token}")
	public ResponseEntity<?> details(@PathVariable("token") String token) throws Exception {

		User user = adminService.details(token);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("update/{token}")
	public ResponseEntity<?> update(@RequestBody User user, @PathVariable("token") String email)
			throws Exception {

		if(!user.getPassword().equals(""))
			user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

		adminService.updateUser(user, email);

		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{email}")
	public ResponseEntity<?> delete(@PathVariable String email) throws Exception {

		adminService.deleteUser(email);

		return new ResponseEntity<>("El usuario ha sido eliminado", HttpStatus.OK);
	}

}

package com.backend.SafeSQL.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.SafeSQL.model.Message;
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

		List<User> lista = adminService.list();
		return lista;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("details/{email}")
	public ResponseEntity<?> details(@PathVariable("email") String email) throws Exception {

		User user = adminService.details(email);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("update/{email}")
	public ResponseEntity<?> update(@RequestBody User user, @PathVariable("email") String email)
			throws Exception {

		if(user.getPassword() != null)
			user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

		adminService.updateUser(user, email);

		return new ResponseEntity<User>(HttpStatus.OK);

	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{email}")
	public ResponseEntity<?> delete(@PathVariable String email) throws Exception {

		adminService.deleteUser(email);

		return new ResponseEntity(new Message("El usuario ha sido eliminado"), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{email}")
	public User getUser(@PathVariable String email) throws Exception {
		return adminService.getUser(email);

	}



}

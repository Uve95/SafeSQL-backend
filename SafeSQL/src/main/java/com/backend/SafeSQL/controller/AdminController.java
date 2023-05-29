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

import com.backend.SafeSQL.model.Message;
import com.backend.SafeSQL.model.User;
import com.backend.SafeSQL.service.AdminService;
import com.backend.SafeSQL.service.MailService;
import com.backend.SafeSQL.service.UserService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")

public class AdminController {
	@Autowired
	private AdminService adminService;



	@GetMapping("/list")
	public ResponseEntity<User[]> findAll() {
		
        User[] lista = adminService.findAll();
        		
        
        return new ResponseEntity<User[]>(lista, HttpStatus.OK);
	}
	
    @GetMapping("/details/{email}")
    public ResponseEntity<?> details(@PathVariable("email") String email) throws Exception{
    	
       
    	User user = adminService.details(email);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
	
    @PutMapping("/update/{email}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable("email") String email) throws Exception{
    	
       
    	adminService.updateUser(email, user);

        return new ResponseEntity(new Message("Usuario actualizado correctamente"), HttpStatus.CREATED);
    }
    

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<?> delete(@PathVariable String email) throws Exception{

    	adminService.deleteUser(email);
    	
        return new ResponseEntity(new Message("El usuario ha sido eliminado"), HttpStatus.OK);
    }
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

	

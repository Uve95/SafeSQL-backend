package com.backend.SafeSQL.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.SafeSQL.dao.UserRepository;
import com.backend.SafeSQL.model.User;

import jakarta.mail.MessagingException;

@Service

public class UserService {

	@Autowired 
	private UserRepository userDAO;
	
	
	//Metodo para guardar usuarios
	public User register(User user) {
		return userDAO.save(user);
		
	}
	
	public void login(JSONObject jso) throws Exception {
		
		User user = new User();
		String email = jso.getString("email");
		String password = jso.getString("password");

		
		if(userDAO.findByEmail(email) == null ) 
			throw new Exception("No existe el usuario con email "+email);
			
		user = userDAO.findByEmail(email);
		
		if(!user.getPassword().equals(password)) 
			throw new Exception("Credenciales invalidas");
		

	}
	


	//Metodo para listar usuarios
	public List<User> findAll() {
		return userDAO.findAll();
	}



	public User getUser(String email) throws Exception {
		
		User user = userDAO.findByEmail(email);

		if(userDAO.findByEmail(email) == null ) 
			throw new Exception("No existe el usuario con email "+email);

		return user;
		
	}


	public User updateUser(String email, User user) throws Exception {
		

		if(userDAO.findByEmail(user.getEmail()) == null ) 
			throw new Exception("No existe el usuario con email "+email);
		
		//User user = userDAO.findByEmail(user.getEmail());
		
		user.setEmail(user.getEmail());
		user.setName(user.getName());
		user.setSurname(user.getSurname());
		user.setPassword(user.getPassword());
		user.setRol("user");
		
		User userUpdate = userDAO.save(user);
		
		return userUpdate;
	}


	public void deleteUser(String email) throws Exception {
		
		if(userDAO.findByEmail(email) == null ) 
			throw new Exception("No existe el usuario con email "+email);
		
		userDAO.deleteById(email);
		
	}



	public void changePassword(JSONObject jso) throws Exception {
		
		User user = new User();
		String email = jso.getString("email");
		String password = jso.getString("password");

		
		if(userDAO.findByEmail(email) == null ) 
			throw new Exception("No existe el usuario con email "+email);
			
		user = userDAO.findByEmail(email);
		user.setPassword(password);
		userDAO.save(user);

	}

	public User details(JSONObject jso) throws Exception {
		
		User user = new User();
		String email = jso.getString("email");

		
		if(userDAO.findByEmail(email) == null ) 
			throw new Exception("No existe el usuario con email "+email);
			
		user = userDAO.findByEmail(email);
		return user;
		
	}


}

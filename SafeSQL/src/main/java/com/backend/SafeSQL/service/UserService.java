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
	public User saveUser(User user) {
		return userDAO.save(user);
		
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


	public User updateUser(String email, User detallesUser) throws Exception {
		

		if(userDAO.findByEmail(detallesUser.getEmail()) == null ) 
			throw new Exception("No existe el usuario con email "+email);
		
		User user = userDAO.findByEmail(detallesUser.getEmail());
		
		user.setEmail(detallesUser.getEmail());
		user.setName(detallesUser.getName());
		user.setSurname(detallesUser.getSurname());
		user.setPassword(detallesUser.getPassword());
		
		User userUpdate = userDAO.save(user);
		
		return userUpdate;
	}


	public void deleteUser(String email) throws Exception {
		
		if(userDAO.findByEmail(email) == null ) 
			throw new Exception("No existe el usuario con email "+email);
		
		userDAO.deleteById(email);
		
	}


	public void loginUser(JSONObject jso) throws Exception {
		
		User user = new User();
		String email = jso.getString("email");
		String password = jso.getString("password");

		
		if(userDAO.findByEmail(email) == null ) 
			throw new Exception("No existe el usuario con email "+email);
			
		user = userDAO.findByEmail(email);
		
		if(!user.getPassword().equals(password)) 
			throw new Exception("Credenciales invalidas");

	}


}

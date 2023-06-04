package com.backend.SafeSQL.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.SafeSQL.dao.UserRepository;
import com.backend.SafeSQL.model.User;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UserRepository userRepository;

	public List<User> list() {
		List<User> lista = userRepository.findAll();

		return lista;
	}

	public User details(String email) throws Exception {

		User user = userRepository.findByEmail(email);

		if (userRepository.findByEmail(email) == null)
			throw new Exception("No existe el usuario con email " + email);

		return user;

	}

	public User updateUser(User user) throws Exception {

		if (userRepository.findByEmail(user.getEmail()) == null)
			throw new Exception("No existe el usuario con email " + user.getEmail());

		User userUpdate = userRepository.findByEmail(user.getEmail());

		if (userUpdate.getName() != "")
			userUpdate.setName(user.getEmail());

		if (userUpdate.getSurname() != "")
			userUpdate.setName(user.getSurname());

		if (userUpdate.getPassword() != "")
			userUpdate.setPassword(user.getPassword());

		userRepository.save(userUpdate);
		return userUpdate;

	}

	public void deleteUser(String email) throws Exception {

		if (userRepository.findByEmail(email) == null)
			throw new Exception("No existe el usuario con email " + email);

		userRepository.deleteById(email);

	}

	/*
	 * public void login(JSONObject jso) throws Exception {
	 * 
	 * User user = new User(); String email = jso.getString("email"); String
	 * password = jso.getString("password");
	 * 
	 * if (userDAO.findByEmail(email) == null) throw new
	 * Exception("No existe el usuario con email " + email);
	 * 
	 * user = userDAO.findByEmail(email);
	 * 
	 * if (!user.getPassword().equals(password)) throw new
	 * Exception("Credenciales invalidas");
	 * 
	 * }
	 */

	public boolean existUser(String email) {

		if (userRepository.findByEmail(email) != null)
			return true;

		return false;
	}

	public User getUser(String email) {
		return userRepository.findByEmail(email);
	}


	

}

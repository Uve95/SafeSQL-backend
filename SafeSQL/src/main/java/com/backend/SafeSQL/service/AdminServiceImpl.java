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

	public User details(String token) throws Exception {

		User user = userRepository.findByToken(token);

		if (userRepository.findByToken(token) == null)
			throw new Exception("No existe el usuario con token " + token);

		return user;

	}

	public User updateUser(User user, String token) throws Exception {

		if (userRepository.findByToken(token) == null)
			throw new Exception("No existe el usuario con token " + token);

		User userUpdate = userRepository.findByToken(token);

		if (user.getName() != "")
			userUpdate.setName(user.getName());

		if (user.getSurname() != "")
			userUpdate.setSurname(user.getSurname());

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

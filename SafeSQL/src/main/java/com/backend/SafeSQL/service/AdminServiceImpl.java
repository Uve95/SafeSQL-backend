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

		return userRepository.findAll();
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

		if (!user.getName().equals(""))
			userUpdate.setName(user.getName());

		if (!user.getSurname().equals(""))
			userUpdate.setSurname(user.getSurname());

		userRepository.save(userUpdate);
		return userUpdate;

	}

	public void deleteUser(String email) throws Exception {

		if (userRepository.findByEmail(email) == null)
			throw new Exception("No existe el usuario con email " + email);

		userRepository.deleteById(email);

	}


	public boolean existUser(String email) {

		if (userRepository.findByEmail(email) != null)
			return true;

		return false;
	}

	public User getUser(String email) {
		return userRepository.findByEmail(email);
	}


	

}

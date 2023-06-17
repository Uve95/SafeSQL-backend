package com.backend.SafeSQL.service;

import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.SafeSQL.dao.RolRepository;
import com.backend.SafeSQL.dao.UserRepository;
import com.backend.SafeSQL.model.User;
import com.backend.SafeSQL.model.UserRol;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RolRepository rolRepository;

	@Autowired
	private MailService mailService;
	
	@Override
	public User saveUser(User user, Set<UserRol> userRoles) throws Exception {

		User userLocal = userRepository.findByEmail(user.getEmail());

		if (userLocal != null) {
			System.out.println("El usuario ya existe");
			throw new Exception("El usuario ya esta registrado");

		} else {
			for (UserRol userRol : userRoles) {
				rolRepository.save(userRol.getRol());
			}

			user.getUserRoles().addAll(userRoles);
			user.setToken(user.createToken());
			userLocal = userRepository.save(user);
		}

		return userLocal;

	}

	@Override
	public User getUser(String email) {
		return userRepository.findByEmail(email);
	}

	public void changePassword(User user) throws Exception {



		if (userRepository.findByToken(user.getToken()) == null)
			throw new Exception("No existe el usuario con token " + user.getToken());

		User userAux = userRepository.findByToken(user.getToken());
		
		userAux.setPassword(user.getPassword());
		userAux.setToken(user.createToken());
		userRepository.save(userAux);

	}

	@Override
	public void forgotPassword(User user) throws Exception {
		
		if (userRepository.findByEmail(user.getEmail()) == null)
			throw new Exception("No existe el usuario con token " + user.getEmail());
		
		User userAux = userRepository.findByEmail(user.getEmail());
		mailService.sendEmail(userAux);
		
		
		



		
	}
	
	
}
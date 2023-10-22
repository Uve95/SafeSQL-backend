package com.backend.SafeSQL.model;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.backend.SafeSQL.dao.UserRepository;

@Component

public class NumberGenerator {

	@Autowired
	private UserRepository userRepository;

	// Programar una tarea para ejecutarse cada 5 minutos
	@Scheduled(fixedRate = 60 * 60 * 1000) // Ejecuta cada 5 minutos (en milisegundos)

	public void ejecutarTarea() {
		List<User> users = userRepository.findAll();

		for (User user : users) {
			String token = Math.round(Math.random() * 99999999) + "";

			user.setToken(token);
			userRepository.save(user);

		}

	}

}
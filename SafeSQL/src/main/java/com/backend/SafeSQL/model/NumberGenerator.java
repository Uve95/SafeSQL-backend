package com.backend.SafeSQL.model;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.backend.SafeSQL.dao.UserRepository;

@Component

public class NumberGenerator {

	@Autowired
	private UserRepository userRepository;

	// Programar una tarea para ejecutarse cada 5 minutos
	@Scheduled(fixedRate = 60 * 60 * 1000) // Ejecuta cada 5 minutos (en milisegundos)

	public void ejecutarTarea() {
		// Generar un número aleatorio
		Random random = new Random();

		List<User> users = userRepository.findAll();

		for (User user : users) {
			String token = Math.round(Math.random() * 99999999) + "";

			user.setToken(token);
			userRepository.save(user);

		}

	}

}
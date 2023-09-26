package com.backend.SafeSQL;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.backend.SafeSQL.dao.UserRepository;
import com.backend.SafeSQL.model.Rol;
import com.backend.SafeSQL.model.User;
import com.backend.SafeSQL.model.UserRol;
import com.backend.SafeSQL.service.UserService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
@ComponentScan
@EnableJpaRepositories

public class SafeSqlApplication {

	@Autowired
	private UserService usuarioService;

	@Autowired
	private static UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {

		SpringApplication.run(SafeSqlApplication.class, args);
	}

}

/*
 * @Component public class CommandLineRunnerImpl implements CommandLineRunner {
 * 
 * @Override public void run(String... args) throws Exception {
 * 
 * 
 * User usuario = new User();
 * 
 * usuario.setName("Maria Victoria"); usuario.setSurname("Alcázar Clemente");
 * usuario.setEmail("vickydaimiel@hotmail.com");
 * usuario.setToken(usuario.createToken());
 * usuario.setPassword(bCryptPasswordEncoder.encode("admin"));
 * 
 * Rol rol = new Rol(); rol.setRolId(1L); rol.setRolName("ADMIN");
 * 
 * Set<UserRol> userRoles = new HashSet<>(); UserRol usuarioRol = new UserRol();
 * usuarioRol.setRol(rol); usuarioRol.setUser(usuario);
 * userRoles.add(usuarioRol);
 * 
 * User usuarioGuardado = usuarioService.saveUser(usuario,userRoles);
 * System.out.println(usuarioGuardado.getUsername());
 * 
 * 
 * } }
 * 
 */

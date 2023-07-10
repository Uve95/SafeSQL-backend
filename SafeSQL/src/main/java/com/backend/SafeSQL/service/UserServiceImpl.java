package com.backend.SafeSQL.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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

	@Override
	public void connectBD(String[] info) {
		ResultSet resultSet = null;
		// String connectionUrl =
		// "jdbc:sqlserver://DESKTOP-4D5JPR3;databaseName=AdventureWorksLT2019;user=Admin;password=Admin;trustServerCertificate=true;";

		if (userRepository.findByEmail(info[1]) != null) {

			User userAux = userRepository.findByEmail(info[1]);

			try (Connection connection = DriverManager.getConnection(info[0]);
					Statement statement = connection.createStatement();) {
				String sqlcheck = "SELECT DB_NAME() AS [Current Database];";
				resultSet = statement.executeQuery(sqlcheck);

				userAux.setInformation(info[0]);
				userRepository.save(userAux);

			} catch (SQLException e) {
				e.printStackTrace();

			}

		}

	}

	@Override
	public ArrayList checklist(String [] info) throws Exception {

		ResultSet resultSet = null;
		ArrayList array = new ArrayList();
		boolean[] listchecks = null;
		for(int i = 0; i <info[0].length();i++) {
			listchecks[i] = Boolean.parseBoolean(info[0]);
			
		}

		//ArrayList<Boolean> listchecks = info.indexOf("'listchecks':", 0);

		if (userRepository.findByEmail(info[1]) != null) {

			User userAux = userRepository.findByEmail(info[1]);

			try (Connection connection = DriverManager.getConnection(info[1]);
					Statement statement = connection.createStatement();) {

				// Create and execute a SELECT SQL statement.
				String check1 = "Select case SERVERPROPERTY('IsIntegratedSecurityOnly')"
						+ "when 1 then 'Windows Authentication'"
						+ "when 0 then 'Windows and SQL Server Authentication'"
						+ "end as [Authentication Mode] ";
				resultSet = statement.executeQuery(check1);

				// Print results from select statement
				while (resultSet.next()) {
					array.add(resultSet.getString(2) + " " + resultSet.getString(9));
				}
				
				userAux.setInformation("");
				userRepository.save(userAux);
				
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}

		return null;
	}
}

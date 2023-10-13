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

	String[] array = new String[71];

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RolRepository rolRepository;

	@Autowired
	private MailService mailService;

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

	public String BDName(String info) throws Exception {

		User userAux = userRepository.findByEmail(info);
		String cadena = userAux.getInformation();
		String[] cadenaInfo = cadena.split(";");
		String[] bd = cadenaInfo[1].split("=");
		
		return  bd[1];
	}

	public String[] checklistConfig(String [] info) throws Exception {

		ResultSet resultSet = null;

		String[] listchecks = info[0].split(",");

		User userAux = userRepository.findByEmail(info[1]);
		String cadena = userAux.getInformation();
		String[] cadenaInfo = cadena.split(";");
		String[] bd = cadenaInfo[1].split("=");
		String[] user = cadenaInfo[2].split("=");
		
		try (Connection connection = DriverManager.getConnection(cadena);
				Statement statement = connection.createStatement();) {

			array[0] = bd[1];

			// --Configuración---------------------------- array/check(1 al 10); listcheck(0
			// al 9);

			// ----Modo de autenticación de SQL Server

			if (listchecks[1].equalsIgnoreCase("true")) {

				String check1 = "USE " + bd[1]
						+ "; SELECT CASE WHEN SERVERPROPERTY('IsIntegratedSecurityOnly') = 1 THEN '0' ELSE '1' END AS 'Authentication Mode';";

				resultSet = statement.executeQuery(check1);

				// Print results from select statement
				while (resultSet.next()) {
					array[1] = resultSet.getString(1);

				}

				resultSet = null;
			} else {
				array[1] = "-1";

			}

			// ----¿Se ha verificado la existencia de troyanos en master.sp_procoption?

			if (listchecks[2].equalsIgnoreCase("true")) {

				String check2 = "USE " + bd[1]
						+ "; SELECT CASE count(*) WHEN 0 THEN '0' ELSE  '1' END FROM sysconfigures WHERE status=0";

				resultSet = statement.executeQuery(check2);

				// Print results from select statement
				while (resultSet.next()) {
					array[2] = resultSet.getString(1);

				}
				resultSet = null;

			} else {
				array[2] = "-1";

				// ---- ¿Existen restricciones de acceso para los procedimientos potencialmente
				// peligrosos?

			}
			if (listchecks[3].equalsIgnoreCase("true")) {

				String check3 = "USE " + bd[1]
						+ "; SELECT CASE count(*) WHEN 0 THEN '0' ELSE  '1' END FROM sysusers a, sysprotects b, master.dbo.syslogins c WHERE a.sid=c.sid AND sysadmin=0 AND a.uid=b.uid AND id=object_id(N'[dbo].[xp_cmdshell]') OR id=object_id(N'[dbo].[sp_sdidebug]') OR id=object_id(N'[dbo].[xp_availablemedia]') OR id=object_id(N'[dbo].[xp_deletemail]') OR id=object_id(N'[dbo].[xp_dirtree]') OR id=object_id(N'[dbo].[xp_dropwebtask]') OR id=object_id(N'[dbo].[xp_dsninfo]') OR id=object_id(N'[dbo].[xp_enumdsn]') OR id=object_id(N'[dbo].[xp_enumerrorlogs]') OR id=object_id(N'[dbo].[xp_enumqueuedtasks]') OR id=object_id(N'[dbo].[xp_eventlog]') OR id=object_id(N'[dbo].[xp_findnextmsg]') OR id=object_id(N'[dbo].[xp_fixeddrives]') OR id=object_id(N'[dbo].[xp_getfiledetails]') OR id=object_id(N'[dbo].[xp_getnetname]') OR id=object_id(N'[dbo].[xp_grantlogin]') OR id=object_id(N'[dbo].[xp_logevent]') OR id=object_id(N'[dbo].[xp_loginconfig]') OR id=object_id(N'[dbo].[xp_logininfo]') OR id=object_id(N'[dbo].[xp_makewebtask]') OR id=object_id(N'[dbo].[xp_msver]') OR id=object_id(N'[dbo].[xp_regread]') OR id=object_id(N'[dbo].[xp_perfend]') OR id=object_id(N'[dbo].[xp_perfmonitor]') OR id=object_id(N'[dbo].[xp_perfsample]') OR id=object_id(N'[dbo].[xp_perfstart]') OR id=object_id(N'[dbo].[xp_readerrorlog]') OR id=object_id(N'[dbo].[xp_readmail]') OR id=object_id(N'[dbo].[xp_revokelogin]') OR id=object_id(N'[dbo].[xp_runwebtask]') OR id=object_id(N'[dbo].[xp_schedulersignal]') OR id=object_id(N'[dbo].[xp_sendmail]') OR id=object_id(N'[dbo].[xp_servicecontrol]') OR id=object_id(N'[dbo].[xp_snmp_getstate]') OR id=object_id(N'[dbo].[xp_snmp_raisetrap]') OR id=object_id(N'[dbo].[xp_sprintf]') OR id=object_id(N'[dbo].[xp_sqlinventory]') OR id=object_id(N'[dbo].[xp_sqlregister]') OR id=object_id(N'[dbo].[xp_sqltrace]') OR id=object_id(N'[dbo].[xp_sscanf]') OR id=object_id(N'[dbo].[xp_startmail]') OR id=object_id(N'[dbo].[xp_stopmail]') OR id=object_id(N'[dbo].[xp_subdirs]') OR id=object_id(N'[dbo].[xp_unc_to_drive]') OR id=object_id(N'[dbo].[xp_dirtree]')";
				resultSet = statement.executeQuery(check3);

				// Print results from select statement
				while (resultSet.next()) {
					array[3] = resultSet.getString(1);
				}
				resultSet = null;

			} else {
				array[3] = "-1";

			}

			// ---- ¿Hay auditorias de acceso configuradas y habilitadas ?

			if (listchecks[4].equalsIgnoreCase("true")) {

				String check4 = "USE " + bd[1]
						+ "; SELECT CASE count(*) WHEN 0 THEN '1' ELSE  '0' END FROM sys.server_audits WHERE is_state_enabled = 1";

				resultSet = statement.executeQuery(check4);

				// Print results from select statement
				while (resultSet.next()) {
					array[4] = resultSet.getString(1);
				}
				resultSet = null;

			} else {
				array[4] = "-1";

			}

			// ----¿Esta actualizada la base de datos con la ultima versión SQL Server?

			if (listchecks[5].equalsIgnoreCase("true")) {

				String check5 = "USE " + bd[1]
						+ "; SELECT CASE WHEN @@VERSION LIKE '%2022%' THEN '0' ELSE '1' END AS 'Version_Check';";

				resultSet = statement.executeQuery(check5);

				// Print results from select statement
				while (resultSet.next()) {
					array[5] = resultSet.getString(1);
				}
				resultSet = null;

			} else {
				array[5] = "-1";

			}
		} catch (SQLException e) {
			e.printStackTrace();

		}

	

		return array;
	}


	public String[] checklistNetwork(String [] info) throws Exception {
		
		ResultSet resultSet = null;

		String[] listchecks = info[0].split(",");

		User userAux = userRepository.findByEmail(info[1]);
		String cadena = userAux.getInformation();
		String[] cadenaInfo = cadena.split(";");
		String[] bd = cadenaInfo[1].split("=");
		String[] user = cadenaInfo[2].split("=");

		try (Connection connection = DriverManager.getConnection(cadena);
				Statement statement = connection.createStatement();) {

			array[0] = bd[1];

			// --Red---------------------------- array/check(11 al 20); listcheck(10 al 19);
			// ----¿Existe un cifrado para las conexiones entrantes?

			if (listchecks[10].equalsIgnoreCase("true")) {
				String check10 = "USE " + bd[1]
						+ "; SELECT CASE WHEN (SELECT  count(distinct (encrypt_option)) FROM sys.dm_exec_connections) >= 1 THEN '1' ELSE '0' END";

				resultSet = statement.executeQuery(check10);

				// Print results from select statement
				while (resultSet.next()) {
					array[10] = resultSet.getString(1);
				}
				resultSet = null;

			} else {
				array[10] = "-1";

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}



		return array;
	}

	public String[] checklistPermission(String[] info) throws Exception {
	
		ResultSet resultSet = null;

		String[] listchecks = info[0].split(",");

		User userAux = userRepository.findByEmail(info[1]);
		String cadena = userAux.getInformation();
		String[] cadenaInfo = cadena.split(";");
		String[] bd = cadenaInfo[1].split("=");
		String[] user = cadenaInfo[2].split("=");
		
		
		try (Connection connection = DriverManager.getConnection(cadena);
				Statement statement = connection.createStatement();) {

			array[0] = bd[1];

			// --Permisos---------------------------- array/check(21 al 30); listcheck(20 al
			// 29);

			// ----¿Existen permisos asociados al usuario guest?

			if (listchecks[20].equalsIgnoreCase("true")) {
				// Create and execute a SELECT SQL statement.
				String check20 = "USE master; GRANT VIEW SERVER STATE TO " + user[1] + "; USE " + bd[1]
						+ "; SELECT CASE count(*) WHEN '0' THEN '0' ELSE  '1' END FROM fn_my_permissions('guest', 'USER');";
				resultSet = statement.executeQuery(check20);

				// Print results from select statement
				while (resultSet.next()) {
					array[20] = resultSet.getString(1);
				}
				resultSet = null;

			} else {
				array[20] = "-1";

			}

			if (listchecks[21].equalsIgnoreCase("true")) {
				// Create and execute a SELECT SQL statement.
				String check21 = "USE " + bd[1]
						+ "; SELECT CASE count(*) WHEN '0' THEN '0' ELSE  '1' END FROM sysusers a, syspermissions b WHERE a.uid=b.grantee AND a.name='public'";

				resultSet = statement.executeQuery(check21);

				// Print results from select statement
				while (resultSet.next()) {
					array[21] = resultSet.getString(1);
				}
				resultSet = null;

			} else {
				array[21] = "-1";

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

	

		return array;
	}

	public String[] checklistPassword(String[] info) throws Exception {


		ResultSet resultSet = null;

		String[] listchecks = info[0].split(",");

		User userAux = userRepository.findByEmail(info[1]);
		String cadena = userAux.getInformation();
		String[] cadenaInfo = cadena.split(";");
		String[] bd = cadenaInfo[1].split("=");
		String[] user = cadenaInfo[2].split("=");
		
		
		try (Connection connection = DriverManager.getConnection(cadena);
				Statement statement = connection.createStatement();) {

			array[0] = bd[1];

			// --Políticas de contraseña---------------------------- array/check(31 al
			// 40);listcheck(30 al 39);

			// --¿Existen contraseñas inseguras distintas del “sa”?

			if (listchecks[30].equalsIgnoreCase("true")) {
				// Create and execute a SELECT SQL statement.
				String check30 = "USE " + bd[1]
						+ "; SELECT CASE WHEN (SELECT count(name) FROM master.dbo.syslogins WHERE password is null) < 30 THEN '0' WHEN (SELECT count(name) FROM master.dbo.syslogins WHERE password is null) >=30 AND (SELECT count(name) FROM master.dbo.syslogins WHERE password is null) <40 THEN '1' ELSE '2' END";

				resultSet = statement.executeQuery(check30);

				// Print results from select statement
				while (resultSet.next()) {
					array[30] = resultSet.getString(1);
				}
				resultSet = null;

			} else {
				array[30] = "-1";

			}

			if (listchecks[31].equalsIgnoreCase("true")) {
				// Create and execute a SELECT SQL statement.
				String check31 = "USE " + bd[1]
						+ "; SELECT CASE count(inicios_sesion.dias_sin_modificar) WHEN '0' THEN '0' ELSE  '1' END FROM (SELECT name, datediff(dd,updatedate,getdate()) AS dias_sin_modificar FROM syslogins) AS inicios_sesion";

				resultSet = statement.executeQuery(check31);

				// Print results from select statement
				while (resultSet.next()) {
					array[31] = resultSet.getString(1);
				}
				resultSet = null;

			} else {
				array[31] = "-1";

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}



		return array;
	}

	public String[] checklistSession(String[] info) throws Exception {

		ResultSet resultSet = null;

		String[] listchecks = info[0].split(",");

		User userAux = userRepository.findByEmail(info[1]);
		String cadena = userAux.getInformation();
		String[] cadenaInfo = cadena.split(";");
		String[] bd = cadenaInfo[1].split("=");
		String[] user = cadenaInfo[2].split("=");

		try (Connection connection = DriverManager.getConnection(cadena);
				Statement statement = connection.createStatement();) {

			array[0] = bd[1];

			// --Revision de inicios de sesión---------------------------- array/check(41 al
			// 50); listcheck(40 al 49);

			// --¿Existen inicios de sesión de SQL habilitados sin una política de
			// contraseña segura habilitada?

			if (listchecks[40].equalsIgnoreCase("true")) {
				// Create and execute a SELECT SQL statement.
				String check40 = "USE " + bd[1]
						+ "; SELECT CASE count(datos.name) WHEN '0' THEN '0' ELSE  '1' END FROM (SELECT [name], RemediationCmd = N'ALTER LOGIN ' + QUOTENAME([name]) + ' WITH CHECK_POLICY = ON;' FROM sys.sql_logins AS s WHERE s.is_policy_checked = 0 AND s.is_disabled = 0 AND s.[name] NOT LIKE N'##MS[_]%##') AS datos";

				resultSet = statement.executeQuery(check40);

				// Print results from select statement
				while (resultSet.next()) {
					array[40] = resultSet.getString(1);
				}
				resultSet = null;

			} else {
				array[40] = "-1";

			}

			if (listchecks[41].equalsIgnoreCase("true")) {
				// Create and execute a SELECT SQL statement.
				String check41 = "USE " + bd[1]
						+ "; SELECT CASE count(*) WHEN '0' THEN '0' ELSE  '1' END AS inicios_de_sesion FROM syslogins";
				resultSet = statement.executeQuery(check41);

				// Print results from select statement
				while (resultSet.next()) {
					array[41] = resultSet.getString(1);
				}
				resultSet = null;

			} else {
				array[41] = "-1";

			}

			if (listchecks[42].equalsIgnoreCase("true")) {
				// Create and execute a SELECT SQL statement.
				String check42 = "USE " + bd[1]
						+ "; CREATE TABLE inicios_fallidos(logdate datetime, processinfo nvarchar(10), text nvarchar(200)); INSERT INTO inicios_fallidos exec sp_readerrorlog 0, 1, 'Login failed' CREATE TABLE inicios_buenos(logdate datetime, processinfo nvarchar(10), text nvarchar(200)); INSERT INTO inicios_buenos exec sp_readerrorlog 0, 1, 'Login' SELECT CASE WHEN (SELECT round(info.resultado*100,2) FROM( SELECT ((SELECT cast(count(*)  AS float) AS intentos_f FROM inicios_fallidos WHERE  convert(date,logdate)= convert(date,getdate()))/ (SELECT  cast( CASE count(*) WHEN '0' THEN '1' END as float)  intentos_b FROM inicios_buenos WHERE convert(date,logdate)= convert(date,getdate()))) resultado) AS info) = 0 THEN '0' ELSE '1' END";

				resultSet = statement.executeQuery(check42);

				// Print results from select statement
				while (resultSet.next()) {
					array[42] = resultSet.getString(1);
				}

				String deleteTables = "USE " + bd[1] + ";DROP TABLE inicios_fallidos; DROP TABLE inicios_buenos;";
				statement.execute(deleteTables);
				resultSet = null;

			} else {
				array[42] = "-1";
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}


		return array;
	}

	public String[] checklistMaintenance(String[] info) throws Exception {

		ResultSet resultSet = null;

		String[] listchecks = info[0].split(",");

		User userAux = userRepository.findByEmail(info[1]);
		String cadena = userAux.getInformation();
		String[] cadenaInfo = cadena.split(";");
		String[] bd = cadenaInfo[1].split("=");
		String[] user = cadenaInfo[2].split("=");
		
		try (Connection connection = DriverManager.getConnection(cadena);
				Statement statement = connection.createStatement();) {

			array[0] = bd[1];

			// --Mantenimiento---------------------------- array/check(51 al
			// 60);listcheck(50 al 59);

			// ----¿Existe un plan de mantenimiento?

			if (listchecks[50].equalsIgnoreCase("true")) {
				// Create and execute a SELECT SQL statement.
				String check50 = "ALTER AUTHORIZATION ON DATABASE ::msdb TO " + user[1]
						+ "; SELECT  CASE count(*) WHEN '0' THEN '1' ELSE '0' END FROM msdb.dbo.sysmaintplan_plans AS s INNER JOIN msdb.dbo.sysmaintplan_subplans AS sp ON sp.plan_id=s.id";
				resultSet = statement.executeQuery(check50);

				// Print results from select statement
				while (resultSet.next()) {
					array[50] = resultSet.getString(1);
				}

				resultSet = null;

			} else {
				array[50] = "-1";

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}


		return array;
	}

	public String[] checklistData(String[] info) throws Exception {

		ResultSet resultSet = null;

		String[] listchecks = info[0].split(",");

		User userAux = userRepository.findByEmail(info[1]);
		String cadena = userAux.getInformation();
		String[] cadenaInfo = cadena.split(";");
		String[] bd = cadenaInfo[1].split("=");
		String[] user = cadenaInfo[2].split("=");
		
		try (Connection connection = DriverManager.getConnection(cadena);
				Statement statement = connection.createStatement();) {

			array[0] = bd[1];

			// --Datos sensibles---------------------------- array/check(61 al
			// 70);listcheck(60 al 69);

			// --¿Existe datos sensibles?

			if (listchecks[60].equalsIgnoreCase("true")) {
				// Create and execute a SELECT SQL statement.
				String check60 = "USE " + bd[1]
						+ "; CREATE TABLE diccionario(nombre nvarchar(50)); insert into diccionario values ('Nombre'),('Apellidos'),('Tel'),('Tlf'), ('Movil'),('Direccion'),('Poblacion'),('Ciudad'),('Pais'),('Postal'),('CP'),('DNI'),('CIF'), ('NIE'),('Pasaporte'),('Identifi'),('Mail'),('Correo'),('Foto'),('Banco'),('Tarjeta'),('Cuenta'), ('Numero'),('IP'),('Name'),('Surname'),('Phone'),('Mobile'),('Cell'),('Celular'),('Address'), ('City'),('Country'),('ZIP'),('Code'),('Birthday'),('Passport'),('Photo'),('Bank'),('Card'), ('Accont'),('Number'),('Error') SELECT CASE WHEN (SELECT round(datos_sensibles.numero*100,2) from( SELECT ( (SELECT cast(count(*)  as float) as contar_sensibles from diccionario d join (SELECT column_name from information_schema.columns) as nombre_columnas ON nombre_columnas.column_name like d.nombre) / (SELECT cast(count(*)  as float) numero from sys.all_columns)) numero) as datos_sensibles) >= 0 AND (SELECT round(datos_sensibles.numero*100,2) from( SELECT ( (SELECT cast(count(*)  as float) as contar_sensibles from diccionario d join (SELECT column_name from information_schema.columns) as nombre_columnas ON nombre_columnas.column_name like d.nombre) / (SELECT cast(count(*)  as float) numero from sys.all_columns)) numero) as datos_sensibles) < 30 THEN '0' WHEN (SELECT round(datos_sensibles.numero*100,2) from( SELECT ( (SELECT cast(count(*)  as float) as contar_sensibles from diccionario d join (SELECT column_name from information_schema.columns) as nombre_columnas ON nombre_columnas.column_name like d.nombre) / (SELECT cast(count(*)  as float) numero from sys.all_columns)) numero) as datos_sensibles) >=30 AND (SELECT round(datos_sensibles.numero*100,2) from( SELECT ( (SELECT cast(count(*)  as float) as contar_sensibles from diccionario d join (SELECT column_name from information_schema.columns) as nombre_columnas ON nombre_columnas.column_name like d.nombre) / (SELECT cast(count(*)  as float) numero from sys.all_columns)) numero) as datos_sensibles) < 60 THEN '1' ELSE '2' END";

				resultSet = statement.executeQuery(check60);

				// Print results from select statement
				while (resultSet.next()) {
					array[60] = resultSet.getString(1);
				}

				String deleteTables = "USE " + bd[1] + "; DROP TABLE diccionario";
				statement.execute(deleteTables);
				resultSet = null;

			} else {
				array[60] = "-1";

			}

			if (listchecks[61].equalsIgnoreCase("true")) {
				// Create and execute a SELECT SQL statement.
				String check61 = "USE " + bd[1]
						+ "; CREATE TABLE diccionario(nombre nvarchar(50)); insert into diccionario values ('Nombre'),('Apellidos'),('Tel'),('Tlf'), ('Movil'),('Direccion'),('Poblacion'),('Ciudad'),('Pais'),('Postal'),('CP'),('DNI'),('CIF'), ('NIE'),('Pasaporte'),('Identifi'),('Mail'),('Correo'),('Foto'),('Banco'),('Tarjeta'),('Cuenta'), ('Numero'),('IP'),('Name'),('Surname'),('Phone'),('Mobile'),('Cell'),('Celular'),('Address'), ('City'),('Country'),('ZIP'),('Code'),('Birthday'),('Passport'),('Photo'),('Bank'),('Card'), ('Accont'),('Number'),('Error') SELECT CASE round(cast(resultado_cifradas.resultado*100 as float),2) WHEN '0' THEN '0' ELSE '1' END from ( SELECT ( SELECT( SELECT( (SELECT  cast(count(*) as float) as contar_encriptados from diccionario d join (SELECT name from sys.columns where [encryption_type] IS NOT NULL) as columnas_encriptadas on columnas_encriptadas.name like d.nombre)) as columnas_encriptadas) / (SELECT  cast(count(*)  as float) as contar_sensibles from diccionario d join (SELECT column_name from information_schema.columns) as nombre_columnas on nombre_columnas.column_name like d.nombre) as columnas_sensibles) resultado) as resultado_cifradas";

				resultSet = statement.executeQuery(check61);

				// Print results from select statement
				while (resultSet.next()) {
					array[61] = resultSet.getString(1);
				}

				String deleteTables = "USE " + bd[1] + "; DROP TABLE diccionario";
				statement.execute(deleteTables);
				resultSet = null;

			} else {
				array[61] = "-1";

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}


		return array;
	}

	public String[] checklistRol(String[] info) throws Exception {

		ResultSet resultSet = null;

		String[] listchecks = info[0].split(",");

		User userAux = userRepository.findByEmail(info[1]);
		String cadena = userAux.getInformation();
		String[] cadenaInfo = cadena.split(";");
		String[] bd = cadenaInfo[1].split("=");
		String[] user = cadenaInfo[2].split("=");
		
		try (Connection connection = DriverManager.getConnection(cadena);
				Statement statement = connection.createStatement();) {

			array[0] = bd[1];

			// --Roles---------------------------- array/check(71 al 80); listcheck(70 al
			// 79);

			// --¿Existe miembros con rol sysadmin ?

			if (listchecks[70].equalsIgnoreCase("true")) {
				// Create and execute a SELECT SQL statement.
				String check70 = "USE " + bd[1]
						+ "; SELECT CASE WHEN (SELECT  round(cast(miembros.rol_miembros*100 as float),2) FROM ( SELECT(( SELECT cast(count(*)  as float) FROM sys.server_principals r INNER JOIN sys.server_role_members m ON r.principal_id = m.role_principal_id INNER JOIN sys.server_principals p ON p.principal_id = m.member_principal_id WHERE r.type = 'R' and r.name = N'sysadmin') /(SELECT cast(count(*)  as float) FROM sys.sysusers)) rol_miembros) AS miembros) >= 0 AND (SELECT  round(cast(miembros.rol_miembros*100 as float),2) FROM ( SELECT(( SELECT cast(count(*)  as float) FROM sys.server_principals r INNER JOIN sys.server_role_members m ON r.principal_id = m.role_principal_id INNER JOIN sys.server_principals p ON p.principal_id = m.member_principal_id WHERE r.type = 'R' and r.name = N'sysadmin') /(SELECT cast(count(*)  as float) FROM sys.sysusers)) rol_miembros) AS miembros) < 25 THEN '0' WHEN (SELECT  round(cast(miembros.rol_miembros*100 as float),2) FROM ( SELECT(( SELECT cast(count(*)  as float) FROM sys.server_principals r INNER JOIN sys.server_role_members m ON r.principal_id = m.role_principal_id INNER JOIN sys.server_principals p ON p.principal_id = m.member_principal_id WHERE r.type = 'R' and r.name = N'sysadmin') /(SELECT cast(count(*)  as float) FROM sys.sysusers)) rol_miembros) AS miembros) >=25 AND (SELECT  round(cast(miembros.rol_miembros*100 as float),2) FROM ( SELECT(( SELECT cast(count(*)  as float) FROM sys.server_principals r INNER JOIN sys.server_role_members m ON r.principal_id = m.role_principal_id INNER JOIN sys.server_principals p ON p.principal_id = m.member_principal_id WHERE r.type = 'R' and r.name = N'sysadmin') /(SELECT cast(count(*)  as float) FROM sys.sysusers)) rol_miembros) AS miembros) < 50 THEN '1' ELSE '2' END";

				resultSet = statement.executeQuery(check70);

				// Print results from select statement
				while (resultSet.next()) {
					array[70] = resultSet.getString(1);
				}
				resultSet = null;

			} else {
				array[70] = "-1";

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return array;
	}

	public void deleteInfo(String[] info) throws Exception {
		User userAux = userRepository.findByEmail(info[1]);

		userAux.setInformation("");
		userRepository.save(userAux);
	}

}

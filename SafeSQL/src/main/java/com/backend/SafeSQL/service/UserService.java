package com.backend.SafeSQL.service;

import java.util.ArrayList;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;

import com.backend.SafeSQL.model.User;
import com.backend.SafeSQL.model.UserRol;

public interface UserService {

	public User saveUser(User user, Set<UserRol> userRoles) throws Exception;

	public User getUser(String email) throws Exception;

	public void changePassword(User user) throws Exception;

	public void forgotPassword(User user) throws Exception;

	public void connectBD(String[] info) throws Exception;

	public User updateUser(User user, String email) throws Exception;
	
	public String[] checklistConfig(String[] info) throws Exception;

	public String[] checklistNetwork(String[] info) throws Exception;

	public String[] checklistPermission(String[] info) throws Exception;

	public String[] checklistPassword(String[] info) throws Exception;

	public String[] checklistSession(String[] info) throws Exception;

	public String[] checklistMaintenance(String[] info) throws Exception;

	public String[] checklistData(String[] info) throws Exception;

	public String[] checklistRol(String[] info) throws Exception;

	public String BDName(String info) throws Exception;

	public void deleteInfo(String info) throws Exception;

	public String getToken(String info) throws Exception;



}

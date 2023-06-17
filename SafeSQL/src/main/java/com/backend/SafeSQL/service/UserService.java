package com.backend.SafeSQL.service;

import java.util.Set;

import org.json.JSONObject;

import com.backend.SafeSQL.model.User;
import com.backend.SafeSQL.model.UserRol;

public interface UserService {

	public User saveUser(User user, Set<UserRol> userRoles) throws Exception;

	public User getUser(String email) throws Exception;

	public void changePassword(User user) throws Exception;

	public void forgotPassword(User user) throws Exception;

}



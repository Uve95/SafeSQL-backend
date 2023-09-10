package com.backend.SafeSQL.service;

import java.util.List;
import com.backend.SafeSQL.model.User;

public interface AdminService {

	public List<User> list() throws Exception;

	public User details(String email) throws Exception;

	public User getUser(String email);

	public void deleteUser(String email) throws Exception;

	public User updateUser(User user, String email) throws Exception;

	public boolean existUser(String email);

}

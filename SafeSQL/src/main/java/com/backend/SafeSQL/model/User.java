package com.backend.SafeSQL.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")

public class User {
	@Id
	private String email; 
	private String name;
	private String surname; 
	private String password;
	private long token = this.createToken();
	
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public long getToken() {
		return token;
	}

	public void setToken(long token) {
		this.token = token;
	}
	
	public long createToken() {
    	long token = Math.round(Math.random()*99999999);
		return token;
	}
	
}

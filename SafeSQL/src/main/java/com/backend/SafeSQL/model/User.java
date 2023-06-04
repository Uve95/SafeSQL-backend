package com.backend.SafeSQL.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")

public class User implements UserDetails{
	@Id
	private String email; 
	private String name;
	private String surname; 
	private String password;
	private String token = this.createToken();
	private boolean enabled = true;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    @JsonIgnore

	private Set<UserRol> userRoles = new HashSet<>();
	
	public User() {}

	public User(String email, String name, String surname, String password, String token, boolean enable) {
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.token = token;
		this.enabled = enable;
	}
	

	public String createToken() {
    	String token = Math.round(Math.random()*99999999)+"";
		return token;
	}



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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enable) {
		this.enabled = enabled;
	}

	public Set<UserRol> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRol> userRoles) {
		this.userRoles = userRoles;
	}

	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Set<Authority> authorities = new HashSet<>();
		this.userRoles.forEach(userRoles -> {
			authorities.add(new Authority(userRoles.getRol().getRolName()));
		});
		return authorities;
	}



}

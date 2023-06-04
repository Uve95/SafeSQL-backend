package com.backend.SafeSQL.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.SafeSQL.dao.UserRepository;
import com.backend.SafeSQL.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = this.userRepository.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		return user;
	}

}

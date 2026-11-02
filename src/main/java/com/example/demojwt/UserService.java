package com.example.demojwt;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final PasswordEncoder passwordEncoder;
	
	public UserService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	public User getUserByEmail(String email) {
		if ("user@gmail.com".equals(email)) {
			return new User("user@gmail.com", passwordEncoder.encode("user@123"));
		} else {
			return null;
		}
	}
	
}

package com.security.advanced.service;

import java.util.Optional;

import com.security.advanced.entity.User;

public interface UserService {
	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);
	
	void create(User user);
}

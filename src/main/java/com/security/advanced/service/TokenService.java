package com.security.advanced.service;

import java.util.List;
import java.util.Optional;

import com.security.advanced.entity.Token;

public interface TokenService {
	
	List<Token> findAllValidTokensByUser(Integer userId);
	
	Optional<Token> findByToken(String token);
	
	void create(Token token);
	
}

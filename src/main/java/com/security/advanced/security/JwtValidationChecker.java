package com.security.advanced.security;

import org.springframework.stereotype.Component;

import com.security.advanced.dataAccess.TokenRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtValidationChecker {
	private final TokenRepository tokenRepository;
	
	public String checkIfTokenValid(String token) {
		var isTokenValid = tokenRepository.findByToken(token)
				.map(t -> !t.isExpired() && !t.isRevoked())
				.orElse(false);
		if(!isTokenValid) {
			throw new RuntimeException("Authentication token is not valid");
		}
		return token;
	}
	
}

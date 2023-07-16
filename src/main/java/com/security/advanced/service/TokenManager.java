package com.security.advanced.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.security.advanced.dataAccess.TokenRepository;
import com.security.advanced.entity.Token;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenManager implements TokenService {
	private final TokenRepository tokenRepository;
	
	@Override
	public List<Token> findAllValidTokensByUser(Integer userId) {
		return tokenRepository.findAllValidTokensByUser(userId);
	}

	@Override
	public Optional<Token> findByToken(String token) {
		return tokenRepository.findByToken(token);
	}

	@Override
	public void create(Token token) {
		tokenRepository.save(token);
	}

}

package com.security.advanced.service;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
	private final TokenService tokenService;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		var header = request.getHeader("Authorization");
		
		if(!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
			return;
		}
		
		final String jwt = header.substring(7);
		var storedToken = tokenService.findByToken(jwt).orElse(null);
		
		if(storedToken != null) {			
			storedToken.setExpired(true);
			storedToken.setRevoked(true);
			tokenService.create(storedToken);
		}
		
	}

}

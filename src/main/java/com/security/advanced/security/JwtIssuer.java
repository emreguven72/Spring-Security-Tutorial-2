package com.security.advanced.security;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtIssuer {
	private final JwtProperties jwtProperties;
	
	public String issue(int id, String email, List<String> roles) {
		var token = jwtProperties.getSECRET_KEY();
				
		return JWT.create()
				.withSubject(String.valueOf(id))
				.withExpiresAt(Instant.now().plus(Duration.of(15, ChronoUnit.DAYS))) //it will expires in 1 day
				.withClaim("email", email)
				.withClaim("authorities", roles)
				.sign(Algorithm.HMAC256(token));
	}
	
}

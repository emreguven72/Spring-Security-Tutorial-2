package com.security.advanced.security;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtDecoder {
	private final JwtProperties jwtProperties;
	
	public DecodedJWT decode(String token) {		
		return JWT.require(Algorithm.HMAC256(this.jwtProperties.getSECRET_KEY()))
					.build()
					.verify(token);
	}
	
}

package com.security.advanced.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.advanced.model.LoginRequest;
import com.security.advanced.model.LoginResponse;
import com.security.advanced.security.JwtIssuer;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	private final JwtIssuer jwtIssuer;

	@PostMapping("/authenticate")
	public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) {
		var token = this.jwtIssuer.issue(1, loginRequest.getEmail(), List.of("USER"));
		
		return LoginResponse.builder()
				.accessToken(token)
				.build();
	}
	
}

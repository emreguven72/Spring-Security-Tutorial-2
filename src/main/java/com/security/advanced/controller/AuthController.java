package com.security.advanced.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.advanced.model.LoginRequest;
import com.security.advanced.model.LoginResponse;
import com.security.advanced.model.RegisterRequest;
import com.security.advanced.service.AuthService;
import com.security.advanced.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	
	@PostMapping("/authenticate")
	public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) {
		return authService.attemptLogin(loginRequest);
	}
	
	@PostMapping("/register")
	public void register(@RequestBody RegisterRequest registerRequest) {
		authService.register(registerRequest);
	}
	
}

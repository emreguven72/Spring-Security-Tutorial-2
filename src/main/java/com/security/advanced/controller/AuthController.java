package com.security.advanced.controller;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.advanced.entity.User;
import com.security.advanced.model.LoginRequest;
import com.security.advanced.model.LoginResponse;
import com.security.advanced.security.JwtIssuer;
import com.security.advanced.security.UserPrincipal;
import com.security.advanced.service.AuthService;
import com.security.advanced.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	private final UserService userService;
	private final AuthService authService;
	
	@PostMapping("/authenticate")
	public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) {
		return authService.attemptLogin(loginRequest.getEmail(), loginRequest.getPassword());
	}
	
	@PostMapping("/register")
	public void register(@RequestBody User user) {
		userService.create(user);
	}
	
}

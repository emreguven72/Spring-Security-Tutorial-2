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
import com.security.advanced.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	private final UserService userService;
	private final JwtIssuer jwtIssuer;
	private final AuthenticationManager authenticationManager;

	@PostMapping("/authenticate")
	public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) {
		var authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
		); //if credentials are invalid the code will fail here
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		var principal = (UserPrincipal) authentication.getPrincipal();
		
		var roles = principal.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.toList();
		
		var token = this.jwtIssuer.issue(principal.getId(), principal.getEmail(), roles);
		
		return LoginResponse.builder()
				.accessToken(token)
				.build();
	}
	
	@PostMapping("/register")
	public void register(@RequestBody User user) {
		userService.create(user);
	}
	
}

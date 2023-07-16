package com.security.advanced.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.security.advanced.entity.User;
import com.security.advanced.model.LoginRequest;
import com.security.advanced.model.LoginResponse;
import com.security.advanced.model.RegisterRequest;
import com.security.advanced.security.JwtIssuer;
import com.security.advanced.security.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final JwtIssuer jwtIssuer;
	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	
	public LoginResponse attemptLogin(LoginRequest loginRequest) {
		var authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
		);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		var principal = (UserPrincipal) authentication.getPrincipal();
		var roles = principal.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.toList();
		
		var token = jwtIssuer.issue(principal.getId(), principal.getEmail(), roles);
		
		return LoginResponse.builder()
				.accessToken(token)
				.build();
	}
	
	public void register(RegisterRequest registerRequest) {
		User user = User.builder()
				.email(registerRequest.getEmail())
				.password(registerRequest.getPassword())
				.role(registerRequest.getRole())
				.build();
		
		userService.create(user);
	}
	
}

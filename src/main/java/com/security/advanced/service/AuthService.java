package com.security.advanced.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.security.advanced.entity.Token;
import com.security.advanced.entity.TokenType;
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
	private final TokenService tokenService;
	
	public LoginResponse attemptLogin(LoginRequest loginRequest) {
		var authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
		);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		var principal = (UserPrincipal) authentication.getPrincipal();
		var roles = principal.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.toList();
		
		var jwtToken = jwtIssuer.issue(principal.getId(), principal.getEmail(), roles);
		
		var authUser = userService.findByEmail(principal.getEmail());
		
		revokeAllUserTokens(authUser.get()); //we are doing this because we want to have only one active token for every user
		
		createUserToken(authUser.get(), jwtToken);
		
		return LoginResponse.builder()
				.accessToken(jwtToken)
				.build();
	}
	
	public void register(RegisterRequest registerRequest) {
		User user = User.builder()
				.email(registerRequest.getEmail())
				.password(registerRequest.getPassword())
				.role(registerRequest.getRole())
				.build();
		
		User savedUser = userService.create(user);
		
		var jwtToken = jwtIssuer.issue(savedUser.getId(), savedUser.getEmail(), List.of(savedUser.getRole()));
		
		createUserToken(savedUser, jwtToken);
	}
	
	private void revokeAllUserTokens(User user) {
		var validUserTokens = tokenService.findAllValidTokensByUser(user.getId());
		
		if(validUserTokens.isEmpty()) {
			return;
		}
		
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
			tokenService.create(token); //updating the existing tokens
		});
	}
	
	private void createUserToken(User user, String jwt) {
		var token = Token.builder()
				.user(user)
				.token(jwt)
				.tokenType(TokenType.BEARER)
				.revoked(false)
				.expired(false)
				.build();
		
		tokenService.create(token);
	}
	
}

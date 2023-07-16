package com.security.advanced.model;

import lombok.Getter;

@Getter
public class RegisterRequest {
	
	private String email;
	private String password;
	private String role;
	
}

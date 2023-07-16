package com.security.advanced.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.advanced.security.UserPrincipal;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/hello")
@RequiredArgsConstructor
public class HelloController {
	
	@GetMapping()
	public ResponseEntity<?> sayHello() {
		return ResponseEntity.ok("Hello from /");
	}
	
	@GetMapping("/secured-hello")
	public ResponseEntity<?> sayHello2(@AuthenticationPrincipal UserPrincipal userPrincipal) {
		return ResponseEntity.ok("If you see this message then you are logged in as user " + userPrincipal.getEmail() + " User ID: " + userPrincipal.getId());
	}
	
	@GetMapping("/admin")
	public ResponseEntity<?> adminHello(@AuthenticationPrincipal UserPrincipal userPrincipal) {
		return ResponseEntity.ok("If you see this message then you are an ADMIN. User ID: " + userPrincipal.getId());
	}
	
	
}

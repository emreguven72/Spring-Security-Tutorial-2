package com.security.advanced.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/hello")
@RequiredArgsConstructor
public class HelloController {
	
	@GetMapping()
	public ResponseEntity<?> sayHello() {
		return ResponseEntity.ok("Hello from /");
	}
	
	@GetMapping("/hello")
	public ResponseEntity<?> sayHello2() {
		return ResponseEntity.ok("Hello from /hello");
	}
	
	@GetMapping("/hello3")
	public ResponseEntity<?> sayHello3() {
		return ResponseEntity.ok("Hello from /hello3");
	}
	
}
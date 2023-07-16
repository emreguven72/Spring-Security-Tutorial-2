package com.security.advanced.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.security.advanced.entity.User;
import com.security.advanced.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService{
	private final UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByEmail(username).orElseThrow();
		
		return UserPrincipal.builder()
				.id(user.getId())
				.email(user.getEmail())
				.password(user.getPassword())
				.authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
				.build();
	}

}

package com.security.advanced.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final CustomUserDetailService customUserDetailService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); //run jwtAuthenticationFilter before UsernamePasswordAuthenticationFilter
		
		httpSecurity
			.cors().disable()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().formLogin().disable()
			.securityMatcher("/**")
			.authorizeHttpRequests(registry -> registry
					.requestMatchers(
							"/api/v1/hello",
							"/api/v1/auth/authenticate",
							"/api/v1/auth/register"
					).permitAll()
					.requestMatchers("/api/v1/hello/admin").hasAuthority("ADMIN")
					.anyRequest().authenticated()
			);
		
		return httpSecurity.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
					.userDetailsService(customUserDetailService)
					.passwordEncoder(passwordEncoder())
					.and().build();
	}
	
}

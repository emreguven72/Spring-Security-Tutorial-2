package com.security.advanced.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtToPrincipalConverter {
	
	public UserPrincipal convert(DecodedJWT jwt) {
		return UserPrincipal.builder()
				.id(Integer.valueOf(jwt.getSubject()))
				.email(jwt.getClaim("email").asString())
				.authorities(extractAuthoritiesFromClaim(jwt))
				.build();
	}
	
	private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt) {
		var claim = jwt.getClaim("authorities");
		
		if(claim.isNull() || claim.isMissing()) {
			return List.of();
		}
		
		return claim.asList(SimpleGrantedAuthority.class);
	}
	
}

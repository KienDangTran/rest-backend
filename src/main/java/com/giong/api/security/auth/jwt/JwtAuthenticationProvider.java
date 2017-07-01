package com.giong.api.security.auth.jwt;

import com.giong.api.domain.Authority;
import com.giong.api.exception.InvalidJwtToken;
import com.giong.api.security.config.JwtSettings;
import com.giong.api.security.model.Scopes;
import com.giong.api.security.model.UserContext;
import com.giong.api.security.model.token.JwtTokenFactory;
import com.giong.api.security.model.token.RawAccessJwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * An {@link AuthenticationProvider} implementation that will use provided
 * instance of {@link com.giong.api.security.model.token.JwtToken} to perform authentication.
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
	private final JwtSettings jwtSettings;

	@Autowired
	public JwtAuthenticationProvider(JwtSettings jwtSettings) {
		this.jwtSettings = jwtSettings;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();
		Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtSettings.getTokenSigningKey());
		String subject = jwsClaims.getBody().getSubject();
		List<String> scopes = jwsClaims.getBody().get(JwtTokenFactory.SCOPES, List.class);
		if (scopes.isEmpty()) {
			throw new InsufficientAuthenticationException("User has no authorities assigned");
		}

		if (scopes.stream().filter(auth -> Scopes.REFRESH_TOKEN.authority().equals(auth)).count() > 0
				|| scopes.stream().filter(auth -> Scopes.ACCESS_TOKEN.authority().equals(auth)).count() == 0) {
			throw new InvalidJwtToken();
		}

		List<GrantedAuthority> authorities = scopes.stream()
												   .map(Authority::new)
												   .collect(Collectors.toList());
		UserContext context = UserContext.create(subject, authorities);

		return new JwtAuthenticationToken(context, context.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
	}
}

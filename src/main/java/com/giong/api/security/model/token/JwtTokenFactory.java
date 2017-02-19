package com.giong.api.security.model.token;

import com.giong.api.exception.InvalidJwtToken;
import com.giong.api.security.config.JwtSettings;
import com.giong.api.security.model.Scopes;
import com.giong.api.security.model.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Factory class that should be always used to create {@link JwtToken}.
 */
@Component
public class JwtTokenFactory {
	private final JwtSettings settings;

	@Autowired
	public JwtTokenFactory(JwtSettings settings) {
		this.settings = settings;
	}

	/**
	 * Factory method for issuing new JWT Tokens.
	 */
	public AccessJwtToken createAccessJwtToken(UserContext userContext) {
		if (StringUtils.isEmpty(userContext.getUsername())) {
			throw new IllegalArgumentException("Cannot create JWT Token without username");
		}

		if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty()) {
			throw new IllegalArgumentException("User doesn't have any privileges");
		}

		Claims claims = Jwts.claims().setSubject(userContext.getUsername());
		claims.put(
				"scopes",
				userContext.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
		);

		Date now = new Date();
		String token = Jwts.builder()
						   .setClaims(claims)
						   .setIssuer(settings.getTokenIssuer())
						   .setIssuedAt(now)
						   .setExpiration(Date.from(now.toInstant().plusSeconds(settings.getTokenExpirationTime())))
						   .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
						   .compact();

		return new AccessJwtToken(token, claims);
	}

	public JwtToken createRefreshToken(UserContext userContext) {
		if (StringUtils.isEmpty(userContext.getUsername())) {
			throw new IllegalArgumentException("Cannot create JWT Token without username");
		}

		Claims claims = Jwts.claims().setSubject(userContext.getUsername());
		claims.put("scopes", Collections.singletonList(Scopes.REFRESH_TOKEN.authority()));

		Date now = new Date();
		String token = Jwts.builder()
						   .setClaims(claims)
						   .setIssuer(settings.getTokenIssuer())
						   .setId(UUID.randomUUID().toString())
						   .setIssuedAt(now)
						   .setExpiration(Date.from(now.toInstant().plusSeconds(settings.getRefreshTokenExpTime())))
						   .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
						   .compact();

		return RefreshToken.create(new RawAccessJwtToken(token), settings.getTokenSigningKey())
						   .orElseThrow(InvalidJwtToken::new);
	}
}

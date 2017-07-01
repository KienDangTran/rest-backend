package com.giong.api.security.model.token;

import com.giong.api.security.config.JwtSettings;
import com.giong.api.security.model.Scopes;
import com.giong.api.security.model.UserContext;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Factory class that should be always used to create {@link JwtToken}.
 */
@Component
public class JwtTokenFactory {
	public static final String SCOPES = "scopes";
	public static final String ACCESS_TOKEN = "token";
	public static final String REFRESH_TOKEN = "refreshToken";

	private final JwtSettings settings;

	@Autowired
	public JwtTokenFactory(JwtSettings settings) {
		this.settings = settings;
	}

	/**
	 * Factory method for issuing new JWT Tokens.
	 */
	private String createJwtToken(String subject, List<String> scopes) {
		if (StringUtils.isEmpty(subject)) {
			throw new IllegalArgumentException("Cannot create JWT Token without subject");
		}

		if (scopes.isEmpty()) {
			throw new IllegalArgumentException("scopes is empty");
		}

		Date now = new Date();

		return Jwts.builder()
				   .setId(UUID.randomUUID().toString())
				   .setSubject(subject)
				   .claim(SCOPES, scopes)
				   .setIssuer(settings.getTokenIssuer())
				   .setIssuedAt(now)
				   .setExpiration(Date.from(now.toInstant().plusSeconds(settings.getTokenExpirationTime())))
				   .signWith(SignatureAlgorithm.HS256, settings.getTokenSigningKey())
				   .compact();
	}

	public String createAccessToken(UserContext userContext) {
		if (StringUtils.isEmpty(userContext.getUsername())) {
			throw new IllegalArgumentException("Cannot create JWT Token without username");
		}

		if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty()) {
			throw new IllegalArgumentException("User doesn't have any privileges");
		}

		List<String> scopes = userContext.getAuthorities()
										 .stream()
										 .map(GrantedAuthority::getAuthority)
										 .collect(Collectors.toList());
		scopes.add(Scopes.ACCESS_TOKEN.authority());
		return this.createJwtToken(userContext.getUsername(), scopes);
	}

	public String createRefreshToken(UserContext userContext) {
		if (StringUtils.isEmpty(userContext.getUsername())) {
			throw new IllegalArgumentException("Cannot create JWT Token without username");
		}

		List<String> scopes = Collections.singletonList(Scopes.REFRESH_TOKEN.authority());

		return this.createJwtToken(userContext.getUsername(), scopes);
	}
}

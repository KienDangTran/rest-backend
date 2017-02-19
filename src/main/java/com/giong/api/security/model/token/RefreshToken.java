package com.giong.api.security.model.token;

import com.giong.api.security.model.Scopes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.List;
import java.util.Optional;

public class RefreshToken implements JwtToken {
	private Jws<Claims> claims;
	private String token;

	private RefreshToken(String token, Jws<Claims> claims) {
		this.token = token;
		this.claims = claims;
	}

	/**
	 * Creates and validates Refresh token
	 */
	public static Optional<RefreshToken> create(RawAccessJwtToken token, String signingKey) {
		Jws<Claims> claims = token.parseClaims(signingKey);

		List<String> scopes = claims.getBody().get("scopes", List.class);
		if (scopes == null || scopes.isEmpty()
				|| !scopes.stream()
						  .filter(scope -> Scopes.REFRESH_TOKEN.authority().equals(scope))
						  .findFirst()
						  .isPresent()) {
			return Optional.empty();
		}

		return Optional.of(new RefreshToken(token.getToken(), claims));
	}

	@Override
	public String getToken() {
		return this.token;
	}

	public Jws<Claims> getClaims() {
		return claims;
	}

	public String getJti() {
		return claims.getBody().getId();
	}

	public String getSubject() {
		return claims.getBody().getSubject();
	}
}

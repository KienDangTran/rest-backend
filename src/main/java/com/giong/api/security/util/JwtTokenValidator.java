package com.giong.api.security.util;

import com.giong.api.dto.oauth.RoleDTO;
import com.giong.api.dto.oauth.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class validates a given token by using the secret configured in the application
 */
@Component
public class JwtTokenValidator {

	@Value("${jwt.secret}")
	private String secret;

	/**
	 * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
	 * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
	 *
	 * @param token the JWT token to parse
	 * @return the User object extracted from specified token or null if a token is invalid.
	 */
	public UserDTO parseToken(String token) {
		UserDTO u = null;
		try {
			Claims body = Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();

			u = new UserDTO();
			u.setUsername(body.getSubject());
			u.setUserId(body.get("userId").toString());
			u.setRoles((List<RoleDTO>) body.get("roles"));
		} catch (JwtException e) {
			// Simply print the exception and null will be returned for the userDto
			e.printStackTrace();
		}
		return u;
	}
}

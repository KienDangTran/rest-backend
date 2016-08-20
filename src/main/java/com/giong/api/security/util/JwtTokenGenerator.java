package com.giong.api.security.util;

import com.giong.api.constant.MasterDataStatus;
import com.giong.api.dto.oauth.AuthorityDTO;
import com.giong.api.dto.oauth.RoleDTO;
import com.giong.api.dto.oauth.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Collections;

/**
 * convenience class to generate a token for testing your requests.
 * Make sure the used secret here matches the on in your application.yml
 */
public class JwtTokenGenerator {
	/**
	 * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
	 * User object. Tokens validity is infinite.
	 *
	 * @param u the user for which the token will be generated
	 * @return the JWT token
	 */
	public static String generateToken(UserDTO u, String secret) {
		Claims claims = Jwts.claims().setSubject(u.getUsername());
		claims.put("userId", u.getUserId() + "");
		claims.put("roles", u.getRoles());

		return Jwts.builder()
			.setClaims(claims)
			.signWith(SignatureAlgorithm.HS512, secret)
			.compact();
	}

	public static void main(String[] args) {
		UserDTO user = new UserDTO();
		user.setUserId("U000000001");
		user.setUsername("admin");
		user.setEnabled(true);
		RoleDTO role = new RoleDTO("ADMIN", "Administrator", MasterDataStatus.ACTIVE,
			Collections.singletonList(new AuthorityDTO("EXE_EMP", "Execute Employee",
				MasterDataStatus.ACTIVE)));
		user.setRoles(Collections.singletonList(role));

		System.out.println("***********************\n\nBearer " + generateToken(user, "giong-@uthorization-2016")
			+ "\n\n****************************");
	}
}

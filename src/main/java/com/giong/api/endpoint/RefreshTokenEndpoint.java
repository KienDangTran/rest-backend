package com.giong.api.endpoint;

import com.giong.api.constant.Endpoint;
import com.giong.api.domain.User;
import com.giong.api.exception.InvalidJwtToken;
import com.giong.api.security.auth.jwt.extractor.TokenExtractor;
import com.giong.api.security.auth.jwt.verifier.TokenVerifier;
import com.giong.api.security.config.JwtSettings;
import com.giong.api.security.config.WebSecurityConfig;
import com.giong.api.security.model.UserContext;
import com.giong.api.security.model.token.JwtToken;
import com.giong.api.security.model.token.JwtTokenFactory;
import com.giong.api.security.model.token.RawAccessJwtToken;
import com.giong.api.security.model.token.RefreshToken;
import com.giong.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class RefreshTokenEndpoint {
	@Autowired
	private JwtTokenFactory tokenFactory;

	@Autowired
	private JwtSettings jwtSettings;

	@Autowired
	private UserService userService;

	@Autowired
	private TokenVerifier tokenVerifier;

	@Autowired
	@Qualifier("jwtHeaderTokenExtractor")
	private TokenExtractor tokenExtractor;

	@RequestMapping(
			value = Endpoint.TOKEN_REFRESH_ENTRY_POINT,
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	public @ResponseBody JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));

		RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
		RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey())
												.orElseThrow(InvalidJwtToken::new);

		String jti = refreshToken.getJti();
		if (!tokenVerifier.verify(jti)) {
			throw new InvalidJwtToken();
		}

		String subject = refreshToken.getSubject();
		User user = userService.loadUserByUsername(subject)
							   .orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));

		if (user.getRoles() == null) { throw new InsufficientAuthenticationException("User has no roles assigned"); }
		List<GrantedAuthority> authorities = user.getAuthorities();

		UserContext userContext = UserContext.create(user.getUsername(), authorities);

		return tokenFactory.createAccessJwtToken(userContext);
	}
}

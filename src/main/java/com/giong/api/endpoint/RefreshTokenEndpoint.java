package com.giong.api.endpoint;

import com.giong.api.constant.Endpoint;
import com.giong.api.domain.User;
import com.giong.api.dto.ResponseWrapper;
import com.giong.api.exception.InvalidJwtToken;
import com.giong.api.security.auth.jwt.extractor.TokenExtractor;
import com.giong.api.security.auth.jwt.verifier.TokenVerifier;
import com.giong.api.security.config.JwtSettings;
import com.giong.api.security.config.WebSecurityConfig;
import com.giong.api.security.model.UserContext;
import com.giong.api.security.model.token.JwtTokenFactory;
import com.giong.api.security.model.token.RawAccessJwtToken;
import com.giong.api.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RefreshTokenEndpoint {
	private final JwtTokenFactory tokenFactory;

	private final JwtSettings jwtSettings;

	private final UserService userService;

	private final TokenVerifier tokenVerifier;

	private final TokenExtractor tokenExtractor;

	@Autowired public RefreshTokenEndpoint(
			UserService userService,
			JwtTokenFactory tokenFactory,
			@Qualifier("jwtHeaderTokenExtractor") TokenExtractor tokenExtractor,
			TokenVerifier tokenVerifier,
			JwtSettings jwtSettings
	) {
		this.userService = userService;
		this.tokenFactory = tokenFactory;
		this.tokenExtractor = tokenExtractor;
		this.tokenVerifier = tokenVerifier;
		this.jwtSettings = jwtSettings;
	}

	@RequestMapping(
			value = Endpoint.TOKEN_REFRESH_ENTRY_POINT,
			method = RequestMethod.POST,
			produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	public @ResponseBody ResponseWrapper refreshToken(HttpServletRequest request)
			throws IOException, ServletException {
		String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));
		RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);

		Jws<Claims> claims = rawToken.parseClaims(jwtSettings.getTokenSigningKey());
		if (!tokenVerifier.verify(claims.getBody().getId())) {
			throw new InvalidJwtToken();
		}

		String subject = claims.getBody().getSubject();
		User user = userService.loadUserByUsername(subject)
							   .orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));

		if (user.getRoles() == null) { throw new InsufficientAuthenticationException("User has no roles assigned"); }
		List<GrantedAuthority> authorities = user.getAuthorities();

		UserContext userContext = UserContext.create(user.getUsername(), authorities);

		Map<String, String> tokenMap = new HashMap<>();
		tokenMap.put(JwtTokenFactory.ACCESS_TOKEN, tokenFactory.createAccessToken(userContext));
		tokenMap.put(JwtTokenFactory.REFRESH_TOKEN, tokenFactory.createRefreshToken(userContext));

		ResponseWrapper response = new ResponseWrapper();
		response.setStatus(HttpStatus.OK.value());
		response.setResult(tokenMap);

		return response;
	}
}

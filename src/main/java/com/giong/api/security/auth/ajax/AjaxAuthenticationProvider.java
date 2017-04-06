package com.giong.api.security.auth.ajax;

import com.giong.api.domain.User;
import com.giong.api.security.model.UserContext;
import com.giong.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {
	private final BCryptPasswordEncoder encoder;

	private final UserService userService;

	@Autowired
	public AjaxAuthenticationProvider(final UserService userService, final BCryptPasswordEncoder encoder) {
		this.userService = userService;
		this.encoder = encoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "No authentication data provided");

		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();

		// TODO: implement user cache

		User user = userService.loadUserByUsername(username)
							   .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

		if (!encoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("Authentication Failed. Password isn't matched.");
		}

		if (user.getAuthorities() == null) {
			throw new InsufficientAuthenticationException("User has no authorities assigned");
		}

		// TODO: implement additional Authentication Checks

		UserContext userContext = UserContext.create(user.getUsername(), user.getAuthorities());

		return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}

package com.giong.api.security.auth.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giong.api.exception.AuthMethodNotSupportedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {
	static final String ORIGIN = "Origin";

	private static final String XML_HTTP_REQUEST = "XMLHttpRequest";

	private static final String X_REQUESTED_WITH = "X-Requested-With";

	private static Logger logger = LoggerFactory.getLogger(AjaxLoginProcessingFilter.class);

	private final AuthenticationSuccessHandler successHandler;

	private final AuthenticationFailureHandler failureHandler;

	private final ObjectMapper objectMapper;

	public AjaxLoginProcessingFilter(
			String defaultProcessUrl,
			AuthenticationSuccessHandler successHandler,
			AuthenticationFailureHandler failureHandler,
			ObjectMapper mapper
	) {
		super(defaultProcessUrl);
		this.successHandler = successHandler;
		this.failureHandler = failureHandler;
		this.objectMapper = mapper;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
			if (StringUtils.isEmpty(request.getHeader(ORIGIN))) {
				String origin = request.getHeader(ORIGIN);
				response.addHeader("Access-Control-Allow-Origin", origin);
				response.addHeader("Access-Control-Allow-Methods", "*");
				response.addHeader("Access-Control-Allow-Credentials", "true");
				response.addHeader("Access-Control-Allow-Headers", "*");
			} else {
				response.getWriter().print("OK");
				response.getWriter().flush();
			}
			return null;
		}
		if (!HttpMethod.POST.name().equals(request.getMethod())
				|| !XML_HTTP_REQUEST.equals(request.getHeader(X_REQUESTED_WITH))) {
			if (logger.isDebugEnabled()) {
				logger.debug("Authentication method not supported. Request method: " + request.getMethod());
			}
			throw new AuthMethodNotSupportedException("Authentication method not supported");
		}

		LoginRequest loginRequest = objectMapper.readValue(request.getReader(), LoginRequest.class);

		if (StringUtils.isEmpty(loginRequest.getUsername()) || StringUtils.isEmpty(loginRequest.getPassword())) {
			throw new AuthenticationServiceException("Username or Password not provided");
		}

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(),
				loginRequest.getPassword()
		);

		return this.getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain chain,
			Authentication authResult
	) throws IOException, ServletException {
		successHandler.onAuthenticationSuccess(request, response, authResult);
	}

	@Override
	protected void unsuccessfulAuthentication(
			HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException failed
	) throws IOException, ServletException {
		SecurityContextHolder.clearContext();
		failureHandler.onAuthenticationFailure(request, response, failed);
	}
}

package com.giong.api.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giong.api.constant.Endpoint;
import com.giong.api.security.RestAuthenticationEntryPoint;
import com.giong.api.security.auth.ajax.AjaxAuthenticationProvider;
import com.giong.api.security.auth.ajax.AjaxLoginProcessingFilter;
import com.giong.api.security.auth.ajax.CorsFilter;
import com.giong.api.security.auth.jwt.JwtAuthenticationProcessingFilter;
import com.giong.api.security.auth.jwt.JwtAuthenticationProvider;
import com.giong.api.security.auth.jwt.SkipPathRequestMatcher;
import com.giong.api.security.auth.jwt.extractor.TokenExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	public static final String JWT_TOKEN_HEADER_PARAM = "Authorization";

	@Autowired
	private RestAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private AuthenticationSuccessHandler successHandler;

	@Autowired
	private AuthenticationFailureHandler failureHandler;

	@Autowired
	private AjaxAuthenticationProvider ajaxAuthenticationProvider;

	@Autowired
	private JwtAuthenticationProvider jwtAuthenticationProvider;

	@Autowired
	private TokenExtractor tokenExtractor;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ObjectMapper objectMapper;

	protected AjaxLoginProcessingFilter ajaxLoginProcessingFilter() throws Exception {
		AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(
				Endpoint.FORM_BASED_LOGIN_ENTRY_POINT,
				successHandler,
				failureHandler,
				objectMapper
		);
		filter.setAuthenticationManager(this.authenticationManager);
		return filter;
	}

	protected JwtAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter() throws Exception {
		List<String> pathsToSkip = Arrays.asList(
				Endpoint.TOKEN_REFRESH_ENTRY_POINT,
				Endpoint.FORM_BASED_LOGIN_ENTRY_POINT
		);
		SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, Endpoint
				.TOKEN_BASED_AUTH_ENTRY_POINT);
		JwtAuthenticationProcessingFilter filter
				= new JwtAuthenticationProcessingFilter(failureHandler, tokenExtractor, matcher);
		filter.setAuthenticationManager(this.authenticationManager);
		return filter;
	}

//	@Bean
//	public FilterRegistrationBean corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("*");
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("*");
//		source.registerCorsConfiguration("/**", config);
//		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//		bean.setOrder(0);
//		return bean;
//	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(ajaxAuthenticationProvider);
		auth.authenticationProvider(jwtAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint(this.authenticationEntryPoint)

			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			.and()
			.authorizeRequests()
			.antMatchers(Endpoint.FORM_BASED_LOGIN_ENTRY_POINT).permitAll()
			.antMatchers(Endpoint.TOKEN_REFRESH_ENTRY_POINT).permitAll()

			.and()
			.authorizeRequests()
			.antMatchers(Endpoint.TOKEN_BASED_AUTH_ENTRY_POINT).authenticated()

			.and()
			.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
			.addFilterBefore(ajaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}

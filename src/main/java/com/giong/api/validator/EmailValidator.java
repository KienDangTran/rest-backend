package com.giong.api.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailValidator {

	private static final String EMAIL_PATTERN =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private final Pattern pattern;

	private Matcher matcher;

	public EmailValidator() {
		this.pattern = Pattern.compile(EmailValidator.EMAIL_PATTERN);
	}

	public boolean valid(final String email) {

		this.matcher = this.pattern.matcher(email);
		return this.matcher.matches();

	}
}
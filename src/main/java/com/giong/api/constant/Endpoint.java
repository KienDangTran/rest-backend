package com.giong.api.constant;

public interface Endpoint {
	String HOME = "/";

	String EMPLOYEE = "/api/employee";

	String FETCH_EMPLOYEE = "/fetch";

	String COUNT_EMPLOYEE = "/count";

	String FORM_BASED_LOGIN_ENTRY_POINT = "/api/auth/login";

	String TOKEN_BASED_AUTH_ENTRY_POINT = "/api/**";

	String TOKEN_REFRESH_ENTRY_POINT = "/api/auth/token";
}

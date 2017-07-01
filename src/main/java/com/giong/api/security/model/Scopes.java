package com.giong.api.security.model;

public enum Scopes {
	ACCESS_TOKEN, REFRESH_TOKEN;

	public String authority() {
		return this.name();
	}
}

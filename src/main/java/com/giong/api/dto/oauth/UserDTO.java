package com.giong.api.dto.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Simple placeholder for info extracted from the JWT
 *
 * @author pascal alma
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	@JsonProperty
	private String userId;

	@JsonProperty
	private String password;

	@JsonProperty
	private String username;

	@JsonProperty
	private boolean accountNonExpired;

	@JsonProperty
	private boolean accountNonLocked;

	@JsonProperty
	private boolean credentialsNonExpired;

	@JsonProperty
	private boolean enabled;

	@JsonProperty
	private List<RoleDTO> roles;
}
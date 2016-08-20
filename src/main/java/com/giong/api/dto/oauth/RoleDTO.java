package com.giong.api.dto.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

	@JsonProperty
	private String roleCode;

	@JsonProperty
	private String roleDesc;

	@JsonProperty
	private String status;

	@JsonProperty
	private List<AuthorityDTO> authority;
}

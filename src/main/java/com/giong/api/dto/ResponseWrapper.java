package com.giong.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper {

	@JsonProperty
	private long timestamp = new Date().getTime();

	@JsonProperty
	private int status;

	@JsonProperty
	private String error;

	@JsonProperty
	private String message;

	@JsonProperty
	private Object response;

}

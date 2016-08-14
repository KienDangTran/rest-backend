package com.giong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbstractResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String RESPONSE_STATUS_FAIL = "FAIL";
	public static final String RESPONSE_STATUS_SUCCESS = "SUCCESS";

	private String status = AbstractResponse.RESPONSE_STATUS_FAIL;
	private Object result = null;

}

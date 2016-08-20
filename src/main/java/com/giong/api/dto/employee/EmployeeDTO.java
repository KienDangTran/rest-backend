package com.giong.api.dto.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.giong.api.constant.AppConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

	@JsonProperty
	private String employeeCode;

	@JsonProperty
	@JsonFormat(pattern = AppConstant.DATE_PATTERN)
	private Date dateOfBirth;

	@JsonProperty
	private String sex;

	@JsonProperty
	private String email;

	@JsonProperty
	private String employeeName;

	@JsonProperty
	private String phoneNo;

	@JsonProperty
	private String status;
}

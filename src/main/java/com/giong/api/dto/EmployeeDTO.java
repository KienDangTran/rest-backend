package com.giong.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.giong.api.constant.AppConstant;
import com.giong.api.domain.Employee;
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
	private String employeeName;

	@JsonProperty
	@JsonFormat(pattern = AppConstant.DATE_PATTERN)
	private Date dateOfBirth;

	@JsonProperty
	private String sex;

	@JsonProperty
	private String email;

	@JsonProperty
	private String phoneNo;

	@JsonProperty
	private String status;

	public EmployeeDTO(Employee employee) {
		this.employeeCode = employee.getEmployeeCode();
		this.employeeName = employee.getEmployeeName();
		this.dateOfBirth = employee.getDateOfBirth();
		this.sex = employee.getSex();
		this.email = employee.getEmail();
		this.phoneNo = employee.getPhoneNo();
		this.status = employee.getStatus();
	}
}

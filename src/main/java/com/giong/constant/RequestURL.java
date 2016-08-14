package com.giong.constant;

public interface RequestURL {
	String HOME = "/";

	String EMPLOYEE_SUMMARY = "/employee";
	String EMPLOYEE_DETAIL = "/employee/{employeeCode}";
	String EMPLOYEE_ADD = "/employee/add";
	String EMPLOYEE_REMOVE = "/employee/removeEmployee/{employeeCode}";
	String EMPLOYEE_REMOVE_BATCH = "/employee/removeBatchEmployee";
}

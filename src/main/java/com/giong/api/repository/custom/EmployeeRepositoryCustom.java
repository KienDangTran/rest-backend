package com.giong.api.repository.custom;

import com.giong.api.domain.Employee;

import java.util.List;

public interface EmployeeRepositoryCustom {
	List<Employee> fetchEmployees(int pageNo, int pageSize);
}

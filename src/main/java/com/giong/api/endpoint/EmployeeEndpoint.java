package com.giong.api.endpoint;

import com.giong.api.constant.Endpoint;
import com.giong.api.dto.EmployeeDTO;
import com.giong.api.dto.ResponseWrapper;
import com.giong.api.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Endpoint.EMPLOYEE)
public class EmployeeEndpoint {
	private final EmployeeService employeeService;

	@Autowired public EmployeeEndpoint(EmployeeService employeeService) {this.employeeService = employeeService;}

	@RequestMapping(path = Endpoint.FETCH_EMPLOYEE, method = RequestMethod.GET)
	public @ResponseBody ResponseWrapper fetchEmployees(
			@RequestParam(required = false, defaultValue = "1") int pageNo,
			@RequestParam(required = false, defaultValue = "10") int pageSize
	) {
		ResponseWrapper response = new ResponseWrapper();
		List<EmployeeDTO> employeeDTOs = employeeService.fetchEmployees(pageNo, pageSize)
														.stream()
														.map(EmployeeDTO::new)
														.collect(Collectors.toList());
		response.setResult(employeeDTOs);
		response.setStatus(HttpStatus.OK.value());
		return response;
	}

	@RequestMapping(path = Endpoint.COUNT_EMPLOYEE, method = RequestMethod.GET)
	public @ResponseBody ResponseWrapper countEmployees() {
		ResponseWrapper response = new ResponseWrapper();
		response.setResult(employeeService.countEmployees());
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
}

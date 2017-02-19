package com.giong.api.endpoint;

import com.giong.api.constant.Endpoint;
import com.giong.api.dto.EmployeeDTO;
import com.giong.api.dto.ResponseWrapper;
import com.giong.api.service.EmployeeService;
import com.giong.api.validator.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Endpoint.EMPLOYEE)
public class EmployeeEndpoint {
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeValidator employeeValidator;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseWrapper getAllEmployee() {
		ResponseWrapper response = new ResponseWrapper();
		List<EmployeeDTO> employeeDTOs = employeeService.getAllEmployee().stream()
														.map(emp -> new EmployeeDTO(
																emp.getEmployeeCode(),
																emp.getDateOfBirth(),
																emp.getSex(),
																emp.getEmail(),
																emp.getEmployeeName(),
																emp.getPhoneNo(),
																emp.getStatus()
														)).collect(Collectors.toList());
		response.setResponse(employeeDTOs);
		response.setStatus(HttpStatus.OK.value());
		return response;
	}
}

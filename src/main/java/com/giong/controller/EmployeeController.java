package com.giong.controller;

import com.giong.dto.AbstractResponse;
import com.giong.dto.mt.EmployeeDTO;
import com.giong.service.mt.EmployeeService;
import com.giong.validator.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private EmployeeValidator employeeValidator;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody AbstractResponse getAllEmployee() throws Exception {
		AbstractResponse response = new AbstractResponse();
		List<EmployeeDTO> employeeDTOs = employeeService.getAllEmployee().stream()
			.map(emp -> new EmployeeDTO(emp.getEmployeeCode(), emp.getDateOfBirth(), emp.getSex(), emp.getEmail(),
				emp.getEmployeeName(), emp.getPhoneNo(), emp.getStatus())).collect(Collectors.toList());
		response.setResult(employeeDTOs);
		response.setStatus(AbstractResponse.RESPONSE_STATUS_SUCCESS);
		return response;
	}
}

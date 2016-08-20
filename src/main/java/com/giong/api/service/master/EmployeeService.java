package com.giong.api.service.master;

import com.giong.api.constant.Scheme;
import com.giong.api.exception.NotFoundException;
import com.giong.api.persistence.domain.Employee;
import com.giong.api.repository.employee.EmployeeRepository;
import com.giong.api.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Transactional
@Service("employeeService")
@PreAuthorize("hasAuthority('VIEW_EMP')")
public class EmployeeService extends BaseService<Employee, String, EmployeeRepository> {

	@Autowired
	SchemeService schemeService;

	public List<Employee> getAllEmployee() {
		return this.repository.findAll();
	}

	public Employee findEmployeeyByCode(String employeeCode) {
		return this.repository.findOne(employeeCode);
	}

	public void saveOrUpdateEmployee(Employee employee) {
		this.repository.saveAndFlush(employee);
	}

	public Employee createEmptyEmployee() {
		final Employee newEmp = new Employee();
		newEmp.setEmployeeCode(this.schemeService.generateNextId(Scheme.EMPLOYEE));
		newEmp.setPersisted(false);
		return newEmp;
	}

	public void removeEmployee(String employeeCode) throws NotFoundException {
		if (StringUtils.isEmpty(employeeCode))
			return;
		final Employee employee = this.findEmployeeyByCode(employeeCode);
		if (employee == null)
			throw new NotFoundException("Employee " + employeeCode + " is not found!");
		this.repository.delete(employee);
	}

}

package com.giong.service.mt;

import com.giong.constant.Scheme;
import com.giong.exception.NotFoundException;
import com.giong.repository.mt.EmployeeRepository;
import com.giong.persistence.mt.MtEmployee;
import com.giong.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Transactional
@Service("employeeService")
@PreAuthorize("hasAuthority('VIEW_EMP')")
public class EmployeeService extends BaseService<MtEmployee, String, EmployeeRepository> {

	@Autowired
	SchemeService schemeService;

	public List<MtEmployee> getAllEmployee() {
		return this.repository.findAll();
	}

	public MtEmployee findEmployeeyByCode(String employeeCode) {
		return this.repository.findOne(employeeCode);
	}

	public void saveOrUpdateEmployee(MtEmployee employee) {
		this.repository.saveAndFlush(employee);
	}

	public MtEmployee createEmptyEmployee() {
		final MtEmployee newEmp = new MtEmployee();
		newEmp.setEmployeeCode(this.schemeService.generateNextId(Scheme.EMPLOYEE));
		newEmp.setPersisted(false);
		return newEmp;
	}

	public void removeEmployee(String employeeCode) throws NotFoundException {
		if (StringUtils.isEmpty(employeeCode))
			return;
		final MtEmployee employee = this.findEmployeeyByCode(employeeCode);
		if (employee == null)
			throw new NotFoundException("Employee " + employeeCode + " is not found!");
		this.repository.delete(employee);
	}

}

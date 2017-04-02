package com.giong.api.repository.impl;

import com.giong.api.domain.Employee;
import com.giong.api.repository.custom.EmployeeRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Employee> fetchEmployees(int pageNo, int pageSize) {
		return em.createQuery("SELECT e FROM Employee e ORDER BY e.employeeCode")
				 .setFirstResult((pageNo - 1) * pageSize)
				 .setMaxResults(pageSize)
				 .getResultList();
	}
}

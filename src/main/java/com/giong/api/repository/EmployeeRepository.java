package com.giong.api.repository;

import com.giong.api.domain.Employee;
import com.giong.api.repository.custom.EmployeeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String>, EmployeeRepositoryCustom {}

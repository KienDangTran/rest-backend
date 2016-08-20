package com.giong.api.repository.employee;

import com.giong.api.persistence.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}

package com.giong.repository.mt;

import com.giong.persistence.mt.MtEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<MtEmployee, String> {

}

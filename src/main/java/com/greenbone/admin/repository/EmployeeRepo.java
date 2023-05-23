package com.greenbone.admin.repository;

import com.greenbone.admin.model.Employee;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, UUID> {

    Optional<Employee> findTopByEmployeeAbb(String employeeAbb);
}

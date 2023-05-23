package com.greenbone.admin.repository;

import com.greenbone.admin.model.Computer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ComputerRepo extends JpaRepository<Computer, UUID>,
    JpaSpecificationExecutor<Computer> {

    List<Computer> findAll(Specification<Computer> spec);

    List<Computer> findAllByEmployeeId(UUID employeeId);

//    Optional<Computer> findFirstByEmployeeId(UUID employeeId);
//
//    Optional<Computer> findByMacAddress(String macAddress);
//
//    Optional<Computer> findByIpAddress(String ipAddress);
//
    Integer countAllByEmployeeId(UUID employeeId);
}

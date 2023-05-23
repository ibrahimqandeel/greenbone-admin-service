package com.greenbone.admin.service;

import com.greenbone.admin.dto.ComputerDto;
import com.greenbone.admin.dto.EmployeeDto;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    List<EmployeeDto> getAllEmployees();

    List<ComputerDto> getAllEmployeeComputers(UUID employeeId);

    List<ComputerDto> assignComputerToEmployee(UUID employeeId, UUID computerId);

    List<ComputerDto> unAssignComputerFromEmployee(UUID employeeId, UUID computerId);

    UUID getEmployeeIdByAbb(String employeeAbb);
}

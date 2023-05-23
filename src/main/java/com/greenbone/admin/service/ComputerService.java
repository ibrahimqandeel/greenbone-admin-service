package com.greenbone.admin.service;

import com.greenbone.admin.dto.ComputerDto;
import com.greenbone.admin.dto.ComputerFilter;
import java.util.List;
import java.util.UUID;

public interface ComputerService {

    ComputerDto saveComputer(ComputerDto computerDto);

    void deleteComputer(UUID computerId);

    ComputerDto getComputerInfoById(UUID computerId);

    List<ComputerDto> getAllEmployeeComputers(UUID employeeId);

    List<ComputerDto> getAllComputers(ComputerFilter filter);

    Integer getEmployeeComputersCount(UUID employeeId);
}

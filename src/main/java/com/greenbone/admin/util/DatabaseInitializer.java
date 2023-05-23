package com.greenbone.admin.util;

import com.greenbone.admin.dto.ComputerDto;
import com.greenbone.admin.dto.EmployeeDto;
import com.greenbone.admin.service.ComputerServiceImpl;
import com.greenbone.admin.service.EmployeeServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DatabaseInitializer implements ApplicationRunner {

    private final ComputerServiceImpl computerService;
    private final EmployeeServiceImpl employeeServiceImpl;

    @Override
    public void run(ApplicationArguments args) {
        saveComputers();
        saveEmployees();
    }

    private void saveComputers() {
        computerService.saveComputer(ComputerDto.builder()
                           .computerName("comp1")
                           .macAddress("123ABC")
                           .ipAddress("192.168.1.2")
                           .description("Computer #1")
            .build());

        computerService.saveComputer(ComputerDto.builder()
                           .computerName("comp2")
                           .macAddress("456ABC")
                           .ipAddress("192.168.1.3")
                           .description("Computer #2")
            .build());

        computerService.saveComputer(ComputerDto.builder()
                           .computerName("comp3")
                           .macAddress("789ABC")
                           .ipAddress("192.168.1.4")
                           .description("Computer #3")
            .build());

        computerService.saveComputer(ComputerDto.builder()
                           .computerName("iq_mac")
                           .macAddress("123")
                           .ipAddress("192.168.1.5")
                           .description("My personal mac")
            .build());
    }

    private void saveEmployees() {
        employeeServiceImpl.saveEmployee(
            EmployeeDto.builder().employeeName("Ibrahim Qandeel").employeeAbb("iqa")
                .build());

        employeeServiceImpl.saveEmployee(
            EmployeeDto.builder().employeeName("John Doe").employeeAbb("jdo")
                .build());

        employeeServiceImpl.saveEmployee(
            EmployeeDto.builder().employeeName("Jackie Chane").employeeAbb("jac")
                .build());
    }
}


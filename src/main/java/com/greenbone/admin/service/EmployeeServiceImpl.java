package com.greenbone.admin.service;

import com.greenbone.admin.dto.ComputerDto;
import com.greenbone.admin.dto.EmployeeDto;
import com.greenbone.admin.exceptions.AppException;
import com.greenbone.admin.exceptions.ErrorMessageCode;
import com.greenbone.admin.model.Employee;
import com.greenbone.admin.notification.NotificationService;
import com.greenbone.admin.repository.EmployeeRepo;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final ComputerService computerService;
    private final EmployeeRepo employeeRepo;
    private final ModelMapper modelMapper;
    private final NotificationService notificationService;

    public EmployeeServiceImpl(@Lazy ComputerService computerService, EmployeeRepo employeeRepo,
        ModelMapper modelMapper, NotificationService notificationService) {
        this.computerService = computerService;
        this.employeeRepo = employeeRepo;
        this.modelMapper = modelMapper;
        this.notificationService = notificationService;
    }

    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee newEmployee = employeeRepo.save(modelMapper.map(employeeDto, Employee.class));
        return modelMapper.map(newEmployee, EmployeeDto.class);
    }

    public List<EmployeeDto> getAllEmployees() {
        return employeeRepo.findAll()
                           .stream()
                           .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                           .collect(Collectors.toList());
    }

    public List<ComputerDto> getAllEmployeeComputers(UUID employeeId) {
        return computerService.getAllEmployeeComputers(employeeId);
    }

    public List<ComputerDto> assignComputerToEmployee(UUID employeeId, UUID computerId) {
        ComputerDto computer = computerService.getComputerInfoById(computerId);
        if (computer.getEmployeeId() != null && !computer.getEmployeeId().equals(employeeId)) {
            throw new AppException(ErrorMessageCode.COMPUTER_ALREADY_ASSIGNED);
        }
        Optional<Employee> employeeRec = employeeRepo.findById(employeeId);
        employeeRec.ifPresent(employee -> {
            computer.setEmployeeId(employee.getId());
            computerService.saveComputer(computer);
            Integer count = computerService.getEmployeeComputersCount(employeeId);
            if (count > 2) {
                notificationService.sendWarningNotification(employee.getEmployeeAbb());
            }
        });

        return getAllEmployeeComputers(employeeId);
    }

    public List<ComputerDto> unAssignComputerFromEmployee(UUID employeeId, UUID computerId) {
        ComputerDto computer = computerService.getComputerInfoById(computerId);
        if (computer.getEmployeeId() != null && !computer.getEmployeeId().equals(employeeId)) {
            throw new AppException(ErrorMessageCode.COMPUTER_NOT_ASSIGNED_TO_EMPLOYEE);
        }
        computer.setEmployeeId(null);
        computerService.saveComputer(computer);

        return getAllEmployeeComputers(employeeId);
    }

    public UUID getEmployeeIdByAbb(String employeeAbb) {
        return employeeRepo.findTopByEmployeeAbb(employeeAbb).orElse(null).getId();
    }
}
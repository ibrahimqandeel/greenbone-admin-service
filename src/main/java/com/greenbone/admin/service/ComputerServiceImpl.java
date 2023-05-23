package com.greenbone.admin.service;

import static com.greenbone.admin.exceptions.ErrorMessageCode.RESOURCE_NOT_FOUND_ERROR;

import com.greenbone.admin.dto.ComputerDto;
import com.greenbone.admin.dto.ComputerFilter;
import com.greenbone.admin.exceptions.AppException;
import com.greenbone.admin.model.Computer;
import com.greenbone.admin.repository.ComputerRepo;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class ComputerServiceImpl implements ComputerService {

    private final ComputerRepo computerRepo;
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    public ComputerDto saveComputer(ComputerDto computerDto) {
        Computer newComputer = computerRepo.save(modelMapper.map(computerDto, Computer.class));
        return modelMapper.map(newComputer, ComputerDto.class);
    }

    public void deleteComputer(UUID computerId) {
        computerRepo.deleteById(computerId);
    }

    public ComputerDto getComputerInfoById(UUID computerId) {
        Computer computer = computerRepo.findById(computerId)
                                        .orElseThrow(
                                            () -> new AppException(RESOURCE_NOT_FOUND_ERROR));
        return modelMapper.map(computer, ComputerDto.class);
    }

    public List<ComputerDto> getAllEmployeeComputers(UUID employeeId) {
        return computerRepo.findAllByEmployeeId(employeeId)
                           .stream()
                           .map(computer -> modelMapper.map(computer, ComputerDto.class))
                           .collect(Collectors.toList());
    }

    public List<ComputerDto> getAllComputers(ComputerFilter filter) {
        Specification<Computer> specification = Specification.where(null);

        if (filter.getComputerName() != null) {
            specification = specification.and(
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("computerName"),
                    filter.getComputerName()));
        }

        if (filter.getMacAddress() != null) {
            specification = specification.and(
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("macAddress"),
                    filter.getMacAddress()));
        }

        if (filter.getIpAddress() != null) {
            specification = specification.and(
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ipAddress"),
                    filter.getIpAddress()));
        }

        if (filter.getEmployeeAbb() != null) {
            UUID employeeId = employeeService.getEmployeeIdByAbb(filter.getEmployeeAbb());
            if (employeeId != null) {
                specification = specification.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("employeeId"),
                        employeeId));
            }
        }

        return modelMapper.map(computerRepo.findAll(specification),
            new TypeToken<List<ComputerDto>>() {}.getType());
    }

    public Integer getEmployeeComputersCount(UUID employeeId) {
        return computerRepo.countAllByEmployeeId(employeeId);
    }
}

/*
Requirements
The system administrator wants to be able to get all computers -> done
The system administrator wants to be able to get the data of a single computer -> done

The system administrator wants to be able to add a new computer to an employee -> done
The system administrator wants to be able to assign a computer to another employee -> done
The system administrator wants to be able to remove a computer from an employee -> done
The system administrator wants to be able to get all assigned computers for an employee -> done

The system administrator wants to be informed when an employee is assigned 3 or more computers
 */
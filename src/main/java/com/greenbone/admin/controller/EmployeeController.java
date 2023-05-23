package com.greenbone.admin.controller;

import com.greenbone.admin.dto.ComputerDto;
import com.greenbone.admin.dto.EmployeeDto;
import com.greenbone.admin.service.EmployeeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Tag(name = "V1-Employees")
@RestController
@RequestMapping("/v1/employees")
public class EmployeeController {

    private final EmployeeServiceImpl employeeServiceImpl;

    @Operation(description = "Get all employees")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200")})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employees = employeeServiceImpl.getAllEmployees();
        HttpStatus responseStatus = employees.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.status(responseStatus).body(employees);
    }

    @Operation(description = "Get all computers assigned to employee")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200")})
    @GetMapping(path = "/{employeeId}/computers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ComputerDto>> getAllComputers(@PathVariable UUID employeeId) {
        List<ComputerDto> computers = employeeServiceImpl.getAllEmployeeComputers(employeeId);
        HttpStatus responseStatus = computers.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.status(responseStatus).body(computers);
    }

    @Operation(description = "Assign computer to the employee")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "409", description = "The computer is already assigned to another employee")})
    @PostMapping(path = "/{employeeId}/computers/assign-computer")
    public ResponseEntity<List<ComputerDto>> assignComputer(@PathVariable UUID employeeId,
        @Valid @RequestBody ComputerDetailsRequest computerDetailsRequest) {
        List<ComputerDto> computers = employeeServiceImpl.assignComputerToEmployee(employeeId,
            computerDetailsRequest.computerId);
        return new ResponseEntity<>(computers, HttpStatus.OK);
    }

    @Operation(description = "Un-assign computer from employee")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "412", description = "The computer is not assigned to this employee")})
    @PostMapping(path = "/{employeeId}/computers/unassign-computer")
    public ResponseEntity<List<ComputerDto>> unAssignComputer(@PathVariable UUID employeeId,
        @Valid @RequestBody ComputerDetailsRequest computerDetailsRequest) {
        List<ComputerDto> computers = employeeServiceImpl.unAssignComputerFromEmployee(employeeId,
            computerDetailsRequest.computerId);
        return new ResponseEntity<>(computers, HttpStatus.OK);
    }

    @Data
    public static class ComputerDetailsRequest {

        @NotBlank
        private UUID computerId;
    }
}
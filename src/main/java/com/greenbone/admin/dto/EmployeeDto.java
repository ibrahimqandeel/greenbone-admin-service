package com.greenbone.admin.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EmployeeDto {

    private UUID id;
    private String employeeName;
    private String employeeAbb;
}
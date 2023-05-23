package com.greenbone.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ComputerFilter {

    private String macAddress;
    private String computerName;
    private String ipAddress;
    private String employeeAbb;
}

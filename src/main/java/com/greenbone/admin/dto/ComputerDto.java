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
public class ComputerDto {

    private UUID id;
    private String macAddress;
    private String computerName;
    private String ipAddress;
    private UUID employeeId;
    private String description;
}

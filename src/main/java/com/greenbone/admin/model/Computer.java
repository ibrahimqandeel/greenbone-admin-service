package com.greenbone.admin.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Computer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "mac_address", unique = true, nullable = false)
    private String macAddress;
    @Column(name = "computer_name", unique = true, nullable = false)
    private String computerName;
    @Column(name = "ip_address", unique = true, nullable = false)
    private String ipAddress;
    @Column(name = "employee_id", columnDefinition = "BINARY(16)")
    private UUID employeeId;
    @Column(name = "description")
    private String description;
}

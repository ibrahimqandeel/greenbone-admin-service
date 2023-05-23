package com.greenbone.admin.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NotificationRequest {

    private String level;
    private String employeeAbbreviation;
    private String message;
}

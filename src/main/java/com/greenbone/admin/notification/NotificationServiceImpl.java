package com.greenbone.admin.notification;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotifyAdminApiClient notifyAdminApiClient;
    public void sendWarningNotification(String employeeAbb) {
        CompletableFuture.runAsync(() -> {
            try {
                notifyAdminApiClient.sendWarningNotification(NotificationRequest.builder()
                    .level("warning")
                    .employeeAbbreviation(employeeAbb)
                    .message("This employee Has more than 2 computers")
                    .build());
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }, Executors.newFixedThreadPool(10));
    }
}


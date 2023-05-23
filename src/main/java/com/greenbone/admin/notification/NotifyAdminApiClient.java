package com.greenbone.admin.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "admin-notifications", url = "${admin.notification.api.url}")
public interface NotifyAdminApiClient {

    @PostMapping(value = "/notify")
        //    @Headers("Content-Type: application/x-www-form-urlencoded")
    void sendWarningNotification(@RequestBody NotificationRequest notificationRequest);
}

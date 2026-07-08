package com.formoura.notification.controller;

import com.formoura.notification.dto.request.NotificationRequest;
import com.formoura.notification.dto.response.NotificationResponse;
import com.formoura.notification.entity.NotificationStatus;
import com.formoura.notification.entity.NotificationType;
import com.formoura.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(
        name = "Notification APIs",
        description = "Notification Management APIs"
)
public class NotificationController {

    private final NotificationService service;

    // ==========================================
    // Send Notification
    // ==========================================

    @Operation(summary = "Send Notification")
    @PostMapping
    public NotificationResponse sendNotification(
            @Valid @RequestBody NotificationRequest request) {

        return service.sendNotification(request);
    }

    // ==========================================
    // Get Notification By Id
    // ==========================================

    @Operation(summary = "Get Notification By Id")
    @GetMapping("/{id}")
    public NotificationResponse getNotificationById(
            @PathVariable Long id) {

        return service.getNotificationById(id);
    }

    // ==========================================
    // Get All Notifications
    // ==========================================

    @Operation(summary = "Get All Notifications")
    @GetMapping
    public List<NotificationResponse> getAllNotifications() {

        return service.getAllNotifications();
    }

    // ==========================================
    // Get Notifications By User Id
    // ==========================================

    @Operation(summary = "Get Notifications By User Id")
    @GetMapping("/user/{userId}")
    public List<NotificationResponse> getNotificationsByUserId(
            @PathVariable Long userId) {

        return service.getNotificationsByUserId(userId);
    }

    // ==========================================
    // Get Notifications By Email
    // ==========================================

    @Operation(summary = "Get Notifications By Email")
    @GetMapping("/email")
    public List<NotificationResponse> getNotificationsByEmail(
            @RequestParam String email) {

        return service.getNotificationsByEmail(email);
    }

    // ==========================================
    // Get Notifications By Status
    // ==========================================

    @Operation(summary = "Get Notifications By Status")
    @GetMapping("/status/{status}")
    public List<NotificationResponse> getNotificationsByStatus(
            @PathVariable NotificationStatus status) {

        return service.getNotificationsByStatus(status);
    }

    // ==========================================
    // Get Notifications By Type
    // ==========================================

    @Operation(summary = "Get Notifications By Type")
    @GetMapping("/type/{type}")
    public List<NotificationResponse> getNotificationsByType(
            @PathVariable NotificationType type) {

        return service.getNotificationsByType(type);
    }

    // ==========================================
    // Get Notifications Between Dates
    // ==========================================

    @Operation(summary = "Get Notifications Between Dates")
    @GetMapping("/date-range")
    public List<NotificationResponse> getNotificationsBetweenDates(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end) {

        return service.getNotificationsBetweenDates(start, end);
    }

    // ==========================================
    // Update Notification Status
    // ==========================================

    @Operation(summary = "Update Notification Status")
    @PutMapping("/{id}/status")
    public NotificationResponse updateNotificationStatus(

            @PathVariable Long id,

            @RequestParam NotificationStatus status) {

        return service.updateNotificationStatus(id, status);
    }

    // ==========================================
    // Delete Notification
    // ==========================================

    @Operation(summary = "Delete Notification")
    @DeleteMapping("/{id}")
    public void deleteNotification(
            @PathVariable Long id) {

        service.deleteNotification(id);
    }

}
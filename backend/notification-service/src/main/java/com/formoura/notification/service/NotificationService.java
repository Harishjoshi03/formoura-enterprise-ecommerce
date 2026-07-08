package com.formoura.notification.service;

import com.formoura.notification.dto.request.NotificationRequest;
import com.formoura.notification.dto.response.NotificationResponse;
import com.formoura.notification.entity.NotificationStatus;
import com.formoura.notification.entity.NotificationType;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationService {

    NotificationResponse sendNotification(
            NotificationRequest request);

    NotificationResponse getNotificationById(
            Long id);

    List<NotificationResponse> getAllNotifications();

    List<NotificationResponse> getNotificationsByUserId(
            Long userId);

    List<NotificationResponse> getNotificationsByEmail(
            String email);

    List<NotificationResponse> getNotificationsByStatus(
            NotificationStatus status);

    List<NotificationResponse> getNotificationsByType(
            NotificationType type);

    List<NotificationResponse> getNotificationsBetweenDates(
            LocalDateTime start,
            LocalDateTime end);

    NotificationResponse updateNotificationStatus(
            Long id,
            NotificationStatus status);

    void deleteNotification(
            Long id);

}
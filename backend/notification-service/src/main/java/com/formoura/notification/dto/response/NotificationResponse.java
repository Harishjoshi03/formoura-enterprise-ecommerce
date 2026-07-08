package com.formoura.notification.dto.response;

import com.formoura.notification.entity.NotificationStatus;
import com.formoura.notification.entity.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponse {

    private Long id;

    private Long userId;

    private String email;

    private String subject;

    private String message;

    private NotificationType notificationType;

    private NotificationStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime sentAt;

    private LocalDateTime updatedAt;

}
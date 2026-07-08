package com.formoura.notification.dto.request;

import com.formoura.notification.entity.NotificationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationRequest {

    @NotNull(message = "User Id is required")
    private Long userId;

    @Email(message = "Invalid Email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Message is required")
    private String message;

    @NotNull(message = "Notification Type is required")
    private NotificationType notificationType;

}
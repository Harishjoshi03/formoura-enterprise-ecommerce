package com.formoura.notification.kafka;

import com.formoura.notification.dto.request.NotificationRequest;
import com.formoura.notification.entity.NotificationType;
import com.formoura.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService service;

    @KafkaListener(
            topics = "notification-topic",
            groupId = "formoura-group")
    public void consume(NotificationEvent event) {

        NotificationRequest request =
                new NotificationRequest();

        request.setUserId(event.getUserId());

        request.setEmail(event.getEmail());

        request.setSubject(event.getSubject());

        request.setMessage(event.getMessage());

        request.setNotificationType(
                NotificationType.EMAIL);

        service.sendNotification(request);

    }

}
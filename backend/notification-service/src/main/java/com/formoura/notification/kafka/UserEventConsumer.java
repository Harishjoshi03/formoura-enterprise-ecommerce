package com.formoura.notification.kafka;

import com.formoura.event.user.UserRegisteredEvent;
import com.formoura.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventConsumer {

    private final EmailService emailService;

    @KafkaListener(
            topics = KafkaTopics.USER_REGISTERED,
            groupId = "notification-group")
    public void consume(
            UserRegisteredEvent event) {

        log.info("User Registered : {}",
                event.getEmail());

        emailService.sendWelcomeEmail(
                event.getEmail(),
                event.getFullName());

    }

}
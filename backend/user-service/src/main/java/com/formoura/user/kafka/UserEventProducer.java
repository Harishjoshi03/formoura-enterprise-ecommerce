package com.formoura.user.kafka;

import com.formoura.event.user.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(UserRegisteredEvent event) {

        kafkaTemplate.send(
                KafkaTopics.USER_REGISTERED,
                event.getUserId().toString(),
                event);

    }

}
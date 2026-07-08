package com.formoura.order.kafka;

import com.formoura.event.order.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String,Object> kafkaTemplate;

    public void publish(OrderCreatedEvent event){

        kafkaTemplate.send(
                KafkaTopics.ORDER_CREATED,
                event.getOrderId().toString(),
                event);

    }

}
package com.formoura.order.kafka;

import com.formoura.event.inventory.InventoryRollbackEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryRollbackProducer {

    private final KafkaTemplate<String,Object> kafkaTemplate;

    public void publish(
            InventoryRollbackEvent event){

        kafkaTemplate.send(
                "inventory-rollback",
                event);

    }

}
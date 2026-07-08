package com.formoura.inventory.kafka;

import com.formoura.event.order.OrderCreatedEvent;
import com.formoura.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryEventConsumer {

    private  final InventoryService inventoryService;

    @KafkaListener(
            topics = "order-created",
            groupId = "inventory-group")
    public void consume(OrderCreatedEvent event){

        inventoryService.reduceStock(event);

        log.info("Order Received : {}",event.getOrderId());

        log.info("Updating Inventory...");

    }

}
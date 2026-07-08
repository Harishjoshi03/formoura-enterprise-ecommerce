package com.formoura.inventory.kafka;

import com.formoura.event.inventory.InventoryRollbackEvent;
import com.formoura.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryRollbackConsumer {

    private final InventoryService inventoryService;

    @KafkaListener(
            topics = "inventory-rollback",
            groupId = "inventory-group")
    public void rollback(
            InventoryRollbackEvent event){

        inventoryService.rollbackInventory(event);

    }

}
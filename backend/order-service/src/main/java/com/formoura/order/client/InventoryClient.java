package com.formoura.order.client;

import com.formoura.order.dto.client.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @GetMapping("/api/inventory/product/{productId}")
    InventoryResponse getInventory(
            @PathVariable Long productId);

}
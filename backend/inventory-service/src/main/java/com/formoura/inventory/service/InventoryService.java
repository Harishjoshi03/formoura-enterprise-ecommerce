package com.formoura.inventory.service;

import com.formoura.event.inventory.InventoryRollbackEvent;
import com.formoura.event.order.OrderCreatedEvent;
import com.formoura.inventory.dto.request.InventoryRequest;
import com.formoura.inventory.dto.response.InventoryResponse;
import org.springframework.data.domain.Page;

public interface InventoryService {

    InventoryResponse createInventory(InventoryRequest request);

    InventoryResponse updateInventory(Long productId,
                                      InventoryRequest request);

    InventoryResponse getInventory(Long productId);

    void deleteInventory(Long productId);

    Page<InventoryResponse> getAllInventory(int page,
                                            int size,
                                            String sortBy);

    Page<InventoryResponse> getLowStockProducts(int quantity,
                                                int page,
                                                int size);

    Page<InventoryResponse> searchByWarehouse(String warehouse,
                                              int page,
                                              int size);

    InventoryResponse reserveStock(Long productId,
                                   Integer quantity);

    InventoryResponse releaseStock(Long productId,
                                   Integer quantity);

    InventoryResponse reduceStock(Long productId,
                                  Integer quantity);
    void reduceStock(OrderCreatedEvent event);

    void rollbackInventory(InventoryRollbackEvent event);
}
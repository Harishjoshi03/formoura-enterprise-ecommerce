package com.formoura.product.service;

public interface InventoryService {

    void increaseStock(Long productId,
                       Integer quantity);

    void decreaseStock(Long productId,
                       Integer quantity);

    Integer availableStock(Long productId);

}
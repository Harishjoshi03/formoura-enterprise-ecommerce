package com.formoura.order.dto.client;

import lombok.Data;

@Data
public class InventoryResponse {

    private Long id;

    private Long productId;

    private Integer availableQuantity;

    private Boolean inStock;

}
package com.formoura.inventory.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class InventoryResponse {

    private Long id;

    private Long productId;

    private Integer availableQuantity;

    private Integer reservedQuantity;

    private Integer soldQuantity;

    private Integer returnedQuantity;

    private Integer damagedQuantity;

    private Integer minimumStock;

    private String warehouse;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
package com.formoura.inventory.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventoryRequest {

    @NotNull(message = "Product Id is required")
    private Long productId;

    @NotNull(message = "Available Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer availableQuantity;

    @Min(value = 0, message = "Minimum Stock cannot be negative")
    private Integer minimumStock;

    private String warehouse;

}
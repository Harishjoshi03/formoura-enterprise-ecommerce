package com.formoura.event.inventory;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdatedEvent {

    private Long productId;

    private Integer quantity;

}
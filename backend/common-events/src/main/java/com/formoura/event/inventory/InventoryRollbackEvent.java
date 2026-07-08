package com.formoura.event.inventory;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRollbackEvent {

    private Long orderId;

    private Long userId;

    // ProductId -> Quantity
    private Map<Long,Integer> products;

    private LocalDateTime createdAt;

}
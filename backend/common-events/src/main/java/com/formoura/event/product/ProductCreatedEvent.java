package com.formoura.event.product;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreatedEvent {

    private Long productId;

    private String productName;

    private Long categoryId;

}
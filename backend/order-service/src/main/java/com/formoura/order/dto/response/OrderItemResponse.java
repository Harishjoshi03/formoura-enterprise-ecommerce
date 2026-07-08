package com.formoura.order.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {

    private Long id;

    private Long productId;

    private String productName;

    private String imageUrl;

    private String sku;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal totalPrice;

}
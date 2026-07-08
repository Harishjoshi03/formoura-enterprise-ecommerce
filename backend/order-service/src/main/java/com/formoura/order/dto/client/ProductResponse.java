package com.formoura.order.dto.client;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {

    private Long id;

    private String productName;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private Integer quantity;

    private String sku;

    private String brand;

    private String imageUrl;

    private Boolean active;

}
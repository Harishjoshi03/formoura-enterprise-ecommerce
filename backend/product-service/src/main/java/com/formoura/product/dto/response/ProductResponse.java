package com.formoura.product.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {

    private Long id;

    private String productName;

    private String description;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private Integer quantity;

    private String sku;

    private String brand;

    private String imageUrl;

    private Double rating;

    private Integer reviewCount;

    private Boolean active;

    private Long categoryId;

    private String categoryName;

}
package com.formoura.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {

    @NotBlank
    private String productName;

    private String description;

    @NotNull
    private BigDecimal price;

    private BigDecimal discountPrice;

    private Integer quantity;

    private String sku;

    private String brand;

    private String imageUrl;

    private Long categoryId;


}
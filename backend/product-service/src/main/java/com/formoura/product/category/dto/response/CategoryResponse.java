package com.formoura.product.category.dto.response;

import lombok.Data;

@Data
public class CategoryResponse {

    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private Boolean active;
}
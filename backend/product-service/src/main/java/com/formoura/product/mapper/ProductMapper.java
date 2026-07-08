package com.formoura.product.mapper;

import com.formoura.product.dto.request.ProductRequest;
import com.formoura.product.dto.response.ProductResponse;
import com.formoura.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "variants", ignore = true)
    @Mapping(target = "specifications", ignore = true)

    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "reviewCount", ignore = true)

    @Mapping(target = "active", ignore = true)
    @Mapping(target = "featured", ignore = true)
    @Mapping(target = "trending", ignore = true)

    @Mapping(target = "stock", ignore = true)
    @Mapping(target = "stockStatus", ignore = true)

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)

    Product toEntity(ProductRequest request);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse toResponse(Product product);

}
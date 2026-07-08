package com.formoura.product.category.mapper;


import com.formoura.product.category.dto.request.CategoryRequest;
import com.formoura.product.category.dto.response.CategoryResponse;
import com.formoura.product.category.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequest request);

    CategoryResponse toResponse(Category category);

}
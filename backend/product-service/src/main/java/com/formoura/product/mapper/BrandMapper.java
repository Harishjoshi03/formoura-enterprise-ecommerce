package com.formoura.product.mapper;

import com.formoura.product.dto.request.BrandRequest;
import com.formoura.product.dto.response.BrandResponse;
import com.formoura.product.entity.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    Brand toEntity(BrandRequest request);

    BrandResponse toResponse(Brand brand);

}
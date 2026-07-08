package com.formoura.order.mapper;

import com.formoura.order.dto.request.OrderItemRequest;
import com.formoura.order.dto.response.OrderItemResponse;
import com.formoura.order.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem toEntity(OrderItemRequest request);

    OrderItemResponse toResponse(OrderItem orderItem);

}
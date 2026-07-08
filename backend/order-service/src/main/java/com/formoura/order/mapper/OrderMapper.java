package com.formoura.order.mapper;

import com.formoura.order.dto.request.OrderRequest;
import com.formoura.order.dto.response.OrderResponse;
import com.formoura.order.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
                OrderItemMapper.class
        }
)
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderNumber", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Order toEntity(OrderRequest request);

    @Mapping(target = "items", source = "orderItems")
    OrderResponse toResponse(Order order);

}
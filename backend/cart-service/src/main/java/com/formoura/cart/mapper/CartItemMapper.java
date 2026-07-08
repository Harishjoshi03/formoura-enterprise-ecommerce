package com.formoura.cart.mapper;

import com.formoura.cart.dto.response.CartItemResponse;
import com.formoura.cart.entity.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItemResponse toResponse(CartItem cartItem);

}
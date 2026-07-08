package com.formoura.cart.mapper;

import com.formoura.cart.dto.response.CartResponse;
import com.formoura.cart.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
                CartItemMapper.class
        }
)
public interface CartMapper {

    @Mapping(target = "cartItems", source = "items")
    CartResponse toResponse(Cart cart);

}
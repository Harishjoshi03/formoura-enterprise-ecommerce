package com.formoura.cart.dto.response;

import com.formoura.cart.entity.CartStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponse {

    private Long id;

    private Long userId;

    private Integer totalItems;

    private BigDecimal totalAmount;

    private Boolean active;

    private CartStatus status;

    private List<CartItemResponse> cartItems;

}
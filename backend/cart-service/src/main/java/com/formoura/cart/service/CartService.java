package com.formoura.cart.service;

import com.formoura.cart.dto.request.CartRequest;
import com.formoura.cart.dto.request.UpdateCartRequest;
import com.formoura.cart.dto.response.CartResponse;

public interface CartService {

    CartResponse addToCart(Long userId,
                           CartRequest request);

    CartResponse updateCartItem(Long cartItemId,
                                UpdateCartRequest request);

    CartResponse getUserCart(Long userId);

    void removeCartItem(Long cartItemId);

    void clearCart(Long userId);

}
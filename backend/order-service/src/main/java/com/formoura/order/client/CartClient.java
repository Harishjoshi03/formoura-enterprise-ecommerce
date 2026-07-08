package com.formoura.order.client;

import com.formoura.order.dto.client.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "cart-service")
public interface CartClient {

    @GetMapping("/api/cart/user/{userId}")
    CartResponse getCartByUserId(
            @PathVariable Long userId);

    @DeleteMapping("/api/cart/clear/{userId}")
    void clearCart(
            @PathVariable Long userId);

}
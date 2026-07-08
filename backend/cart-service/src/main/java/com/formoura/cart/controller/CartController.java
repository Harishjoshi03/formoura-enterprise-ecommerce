package com.formoura.cart.controller;


import com.formoura.cart.dto.request.CartRequest;
import com.formoura.cart.dto.request.UpdateCartRequest;
import com.formoura.cart.dto.response.CartResponse;
import com.formoura.cart.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
@Tag(
        name = "Cart API",
        description = "Manage Shopping Cart"
)
public class CartController {

    private final CartService service;

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("@roleChecker.isUser()")
    public CartResponse addToCart(
            @PathVariable Long userId,
            @Valid @RequestBody CartRequest request) {

        return service.addToCart(userId, request);
    }

    @PutMapping("/item/{cartItemId}")
    @PreAuthorize("@roleChecker.isUser()")
    public CartResponse updateCartItem(
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartRequest request) {

        return service.updateCartItem(cartItemId, request);
    }

    @GetMapping("/{userId}")
    public CartResponse getUserCart(
            @PathVariable Long userId) {

        return service.getUserCart(userId);
    }

    @DeleteMapping("/item/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@roleChecker.isUser()")
    public void removeCartItem(
            @PathVariable Long cartItemId) {

        service.removeCartItem(cartItemId);
    }

    @DeleteMapping("/clear/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@roleChecker.isUser()")
    public void clearCart(
            @PathVariable Long userId) {

        service.clearCart(userId);
    }

}
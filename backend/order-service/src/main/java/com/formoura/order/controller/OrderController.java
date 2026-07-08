package com.formoura.order.controller;

import com.formoura.order.dto.request.OrderRequest;
import com.formoura.order.dto.response.OrderResponse;
import com.formoura.order.entity.OrderStatus;
import com.formoura.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order API", description = "Order Management APIs")
public class OrderController {

    private final OrderService service;

    // ==========================
    // Place Order
    // ==========================

    @Operation(summary = "Place New Order")
    @PostMapping
    @PreAuthorize("@roleChecker.isUser()")
    public OrderResponse placeOrder(
            @Valid @RequestBody OrderRequest request) {

        return service.placeOrder(request);
    }

    // ==========================
    // Get Order By Id
    // ==========================

    @Operation(summary = "Get Order By Id")
    @GetMapping("/{id}")
    public OrderResponse getOrder(
            @PathVariable Long id) {

        return service.getOrder(id);
    }

    // ==========================
    // Get All Orders
    // ==========================

    @Operation(summary = "Get All Orders")
    @GetMapping
    public List<OrderResponse> getAllOrders() {

        return service.getAllOrders();
    }

    // ==========================
    // Get Orders By User
    // ==========================

    @Operation(summary = "Get Orders By User")
    @GetMapping("/user/{userId}")
    public List<OrderResponse> getOrdersByUser(
            @PathVariable Long userId) {

        return service.getOrdersByUser(userId);
    }

    // ==========================
    // Update Order Status
    // ==========================

    @Operation(summary = "Update Order Status")
    @PutMapping("/{id}/status")
    @PreAuthorize("@roleChecker.isUser()")
    public OrderResponse updateOrderStatus(

            @PathVariable Long id,

            @RequestParam OrderStatus status) {

        return service.updateOrderStatus(id, status);
    }

    // ==========================
    // Cancel Order
    // ==========================

    @Operation(summary = "Cancel Order")
    @DeleteMapping("/{id}")
    @PreAuthorize("@roleChecker.isUser()")
    public String cancelOrder(
            @PathVariable Long id) {

        service.cancelOrder(id);

        return "Order Cancelled Successfully";
    }

    /*@PutMapping("/{id}/status")
    public OrderResponse updateOrderStatus(

            @PathVariable Long id,

            @RequestParam OrderStatus status){

        return service.updateOrderStatus(id,status);

    }*/
}
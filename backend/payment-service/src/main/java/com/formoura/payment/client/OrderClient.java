package com.formoura.payment.client;

import com.formoura.payment.client.dto.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "order-service",
        fallback = OrderClientFallback.class
)
public interface OrderClient {

    @GetMapping("/api/orders/{id}")
    OrderResponse getOrderById(
            @PathVariable Long id);

    @PutMapping("/api/orders/{id}/payment-status")
    void updatePaymentStatus(

            @PathVariable Long id,

            @RequestParam String status);

}
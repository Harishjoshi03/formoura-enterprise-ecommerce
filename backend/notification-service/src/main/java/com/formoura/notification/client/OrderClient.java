package com.formoura.notification.client;

import com.formoura.notification.dto.response.OrderResponse;
import com.formoura.notification.fallback.OrderClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "order-service",
        fallback = OrderClientFallback.class
)
public interface OrderClient {

    @GetMapping("/api/orders/{id}")
    OrderResponse getOrderById(
            @PathVariable Long id);

}
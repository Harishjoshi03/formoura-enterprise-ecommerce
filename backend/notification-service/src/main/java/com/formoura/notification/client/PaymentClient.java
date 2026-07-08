package com.formoura.notification.client;

import com.formoura.notification.client.fallback.PaymentClientFallback;
import com.formoura.notification.dto.response.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "payment-service",
        fallback = PaymentClientFallback.class
)
public interface PaymentClient {

    @GetMapping("/api/payments/{id}")
    PaymentResponse getPaymentById(
            @PathVariable Long id);

}
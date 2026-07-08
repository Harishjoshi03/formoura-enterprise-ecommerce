package com.formoura.payment.client;

import com.formoura.payment.client.dto.OrderResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderClientFallback implements OrderClient {

    @Override
    public OrderResponse getOrderById(Long id) {

        throw new RuntimeException(
                "Order Service is unavailable.");
    }

    @Override
    public void updatePaymentStatus(Long id,
                                    String status) {

        throw new RuntimeException(
                "Unable to update payment status.");
    }

}
package com.formoura.notification.client.fallback;

import com.formoura.notification.client.PaymentClient;
import com.formoura.notification.dto.response.PaymentResponse;
import org.springframework.stereotype.Component;

@Component
public class PaymentClientFallback
        implements PaymentClient {

    @Override
    public PaymentResponse getPaymentById(Long id) {

        PaymentResponse response = new PaymentResponse();

        response.setId(id);

        response.setPaymentId("N/A");

        return response;
    }

}
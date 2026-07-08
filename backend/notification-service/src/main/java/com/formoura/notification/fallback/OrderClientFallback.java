package com.formoura.notification.fallback;


import com.formoura.notification.client.OrderClient;
import com.formoura.notification.dto.response.OrderResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderClientFallback
        implements OrderClient {

    @Override
    public OrderResponse getOrderById(Long id) {

        OrderResponse response = new OrderResponse();

        response.setId(id);

        response.setOrderNumber("N/A");

        return response;
    }

}
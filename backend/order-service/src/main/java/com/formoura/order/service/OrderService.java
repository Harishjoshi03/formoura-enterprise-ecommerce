package com.formoura.order.service;

import com.formoura.order.dto.request.OrderRequest;
import com.formoura.order.dto.response.OrderResponse;
import com.formoura.order.entity.OrderStatus;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest request);

    OrderResponse getOrder(Long id);

    List<OrderResponse> getAllOrders();

    List<OrderResponse> getOrdersByUser(Long userId);

    OrderResponse updateOrderStatus(Long id,
                                   OrderStatus status);

    void cancelOrder(Long id);

}
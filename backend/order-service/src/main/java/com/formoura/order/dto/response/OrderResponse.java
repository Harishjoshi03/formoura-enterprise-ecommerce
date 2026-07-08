package com.formoura.order.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {

    private Long id;

    private String orderNumber;

    private Long userId;

    private BigDecimal totalAmount;

    private String status;

    private String paymentStatus;

    private LocalDateTime createdAt;

    private List<OrderItemResponse> items;

}
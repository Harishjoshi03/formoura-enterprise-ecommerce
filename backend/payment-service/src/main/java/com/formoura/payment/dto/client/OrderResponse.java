package com.formoura.payment.client.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderResponse {

    private Long id;

    private String orderNumber;

    private Long userId;

    private BigDecimal totalAmount;

    private String status;

    private String paymentStatus;

}
package com.formoura.notification.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderResponse {

    private Long id;

    private String orderNumber;

    private BigDecimal totalAmount;

    private String ;

}
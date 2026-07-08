package com.formoura.notification.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentResponse {

    private Long id;

    private String paymentId;

    private String transactionId;

    private BigDecimal amount;

    private String paymentStatus;

}
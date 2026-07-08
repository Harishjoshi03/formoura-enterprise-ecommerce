package com.formoura.payment.dto.request;

import com.formoura.payment.entity.PaymentMethod;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePaymentRequest {

    private Long orderId;

    private Long userId;

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

}
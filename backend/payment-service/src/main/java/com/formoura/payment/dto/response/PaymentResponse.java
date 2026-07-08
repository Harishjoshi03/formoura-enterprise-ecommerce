package com.formoura.payment.dto.response;

import com.formoura.payment.entity.PaymentGateway;
import com.formoura.payment.entity.PaymentMethod;
import com.formoura.payment.entity.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {

    private Long id;

    private String paymentId;

    private Long orderId;

    private Long userId;

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private PaymentGateway paymentGateway;

    private String transactionId;

    private String currency;

    private String remarks;

    private LocalDateTime paymentTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String razorpayOrderId;

    private String razorpayPaymentId;

    private String key;

}
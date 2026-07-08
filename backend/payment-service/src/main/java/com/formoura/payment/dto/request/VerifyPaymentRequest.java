package com.formoura.payment.dto.request;

import lombok.Data;

@Data
public class VerifyPaymentRequest {

    private Long orderId;

    private String razorpayOrderId;

    private String razorpayPaymentId;

    private String razorpaySignature;

}
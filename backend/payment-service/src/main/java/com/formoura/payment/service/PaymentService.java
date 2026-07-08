package com.formoura.payment.service;

import com.formoura.payment.dto.request.PaymentRequest;
import com.formoura.payment.dto.response.PaymentResponse;

import java.util.List;

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest request);

    PaymentResponse getPaymentById(Long id);

    PaymentResponse getPaymentByPaymentId(String paymentId);

    List<PaymentResponse> getPaymentsByUserId(Long userId);

    List<PaymentResponse> getPaymentsByOrderId(Long orderId);

    PaymentResponse updatePaymentStatus(Long id,
                                        String paymentStatus);

    void deletePayment(Long id);

    PaymentResponse paymentSuccess(Long id);

    public PaymentResponse paymentFailed(Long id);

    PaymentResponse refundPayment(Long id);

    PaymentResponse verifyPayment(String transactionId);



}
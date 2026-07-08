package com.formoura.payment.controller;

import com.formoura.payment.dto.request.PaymentRequest;
import com.formoura.payment.dto.response.PaymentResponse;
import com.formoura.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payment APIs", description = "Payment Management APIs")
public class PaymentController {

    private final PaymentService service;

    // ==========================================
    // Create Payment
    // ==========================================

    @Operation(summary = "Create Payment")
    @PostMapping
    @PreAuthorize("@roleChecker.isUser()")
    public PaymentResponse createPayment(
            @Valid @RequestBody PaymentRequest request) {

        return service.createPayment(request);
    }

    // ==========================================
    // Get Payment By Id
    // ==========================================

    @Operation(summary = "Get Payment By Id")
    @GetMapping("/{id}")
    @PreAuthorize("@roleChecker.isUser()")
    public PaymentResponse getPaymentById(
            @PathVariable Long id) {

        return service.getPaymentById(id);
    }

    // ==========================================
    // Get Payment By Payment Id
    // ==========================================

    @Operation(summary = "Get Payment By Payment Id")
    @GetMapping("/payment/{paymentId}")
    @PreAuthorize("@roleChecker.isUser()")
    public PaymentResponse getPaymentByPaymentId(
            @PathVariable String paymentId) {

        return service.getPaymentByPaymentId(paymentId);
    }

    // ==========================================
    // Get Payments By User
    // ==========================================

    @Operation(summary = "Get Payments By User")
    @GetMapping("/user/{userId}")
    @PreAuthorize("@roleChecker.isUser()")
    public List<PaymentResponse> getPaymentsByUserId(
            @PathVariable Long userId) {

        return service.getPaymentsByUserId(userId);
    }

    // ==========================================
    // Get Payments By Order
    // ==========================================

    @Operation(summary = "Get Payments By Order")
    @GetMapping("/order/{orderId}")
    @PreAuthorize("@roleChecker.isUser()")
    public List<PaymentResponse> getPaymentsByOrderId(
            @PathVariable Long orderId) {

        return service.getPaymentsByOrderId(orderId);
    }

    // ==========================================
    // Update Payment Status
    // ==========================================

    @Operation(summary = "Update Payment Status")
    @PutMapping("/{id}/status")
    @PreAuthorize("@roleChecker.isUser()")
    public PaymentResponse updatePaymentStatus(

            @PathVariable Long id,

            @RequestParam String status) {

        return service.updatePaymentStatus(id, status);
    }

    // ==========================================
    // Payment Success
    // ==========================================

    @Operation(summary = "Mark Payment Success")
    @PutMapping("/{id}/success")
    public PaymentResponse paymentSuccess(
            @PathVariable Long id) {

        return service.paymentSuccess(id);
    }

    // ==========================================
    // Payment Failed
    // ==========================================

    @Operation(summary = "Mark Payment Failed")
    @PutMapping("/{id}/failed")
    public PaymentResponse paymentFailed(
            @PathVariable Long id) {

        return service.paymentFailed(id);
    }

    // ==========================================
    // Refund Payment
    // ==========================================

    @Operation(summary = "Refund Payment")
    @PutMapping("/{id}/refund")
    public PaymentResponse refundPayment(
            @PathVariable Long id) {

        return service.refundPayment(id);
    }

    // ==========================================
    // Verify Payment
    // ==========================================

    @Operation(summary = "Verify Payment By Transaction Id")
    @GetMapping("/verify/{transactionId}")
    public PaymentResponse verifyPayment(
            @PathVariable String transactionId) {

        return service.verifyPayment(transactionId);
    }

    // ==========================================
    // Delete Payment
    // ==========================================

    @Operation(summary = "Delete Payment")
    @DeleteMapping("/{id}")
    public void deletePayment(
            @PathVariable Long id) {

        service.deletePayment(id);
    }

}

package com.formoura.payment.serviceImp;

import com.formoura.exception.exception.BusinessException;
import com.formoura.payment.client.OrderClient;
import com.formoura.payment.client.dto.OrderResponse;
import com.formoura.payment.dto.request.PaymentRequest;
import com.formoura.payment.dto.response.PaymentResponse;
import com.formoura.payment.entity.Payment;
import com.formoura.payment.entity.PaymentStatus;
import com.formoura.payment.mapper.PaymentMapper;
import com.formoura.payment.repository.PaymentRepository;
import com.formoura.payment.service.PaymentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    private final PaymentMapper mapper;

    private final OrderClient orderClient;

    // ===================================================
    // Create Payment
    // ===================================================

    @Override
    @CircuitBreaker(
            name = "orderService",
            fallbackMethod = "paymentFallback")
    public PaymentResponse createPayment(PaymentRequest request) {

        OrderResponse order =
                orderClient.getOrderById(request.getOrderId());

        if (order == null) {
            throw new BusinessException("Order Not Found");
        }

        Payment payment = mapper.toEntity(request);

        payment.setPaymentId(generatePaymentId());

        payment.setTransactionId(generateTransactionId());

        payment.setPaymentStatus(PaymentStatus.PENDING);

        payment.setPaymentTime(LocalDateTime.now());

        Payment savedPayment = repository.save(payment);

        return mapper.toResponse(savedPayment);
    }

    // ===================================================
    // Get Payment By Id
    // ===================================================

    @Override
    public PaymentResponse getPaymentById(Long id) {

        Payment payment = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Payment Not Found"));

        return mapper.toResponse(payment);
    }

    // ===================================================
    // Get Payment By Payment Id
    // ===================================================

    @Override
    public PaymentResponse getPaymentByPaymentId(String paymentId) {

        Payment payment = repository.findByPaymentId(paymentId)
                .orElseThrow(() ->
                        new BusinessException("Payment Not Found"));

        return mapper.toResponse(payment);
    }

    // ===================================================
    // Get Payments By User
    // ===================================================

    @Override
    public List<PaymentResponse> getPaymentsByUserId(Long userId) {

        return repository.findByUserId(userId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    // ===================================================
    // Get Payments By Order
    // ===================================================

    @Override
    public List<PaymentResponse> getPaymentsByOrderId(Long orderId) {

        return repository.findByOrderId(orderId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    // ===================================================
    // Update Payment Status
    // ===================================================

    @Override
    public PaymentResponse updatePaymentStatus(Long id,
                                               String paymentStatus) {

        Payment payment = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Payment Not Found"));

        payment.setPaymentStatus(
                PaymentStatus.valueOf(paymentStatus.toUpperCase()));

        Payment updatedPayment = repository.save(payment);

        return mapper.toResponse(updatedPayment);
    }

    // ===================================================
    // Delete Payment
    // ===================================================

    @Override
    public void deletePayment(Long id) {

        Payment payment = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Payment Not Found"));

        repository.delete(payment);
    }

    // ===================================================
    // Helper Methods
    // ===================================================

    private String generatePaymentId() {

        return "PAY-"
                + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8)
                .toUpperCase();
    }

    private String generateTransactionId() {

        return "TXN-"
                + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();
    }

// =========================================
// Payment Success
// =========================================

    @CircuitBreaker(
            name = "orderService",
            fallbackMethod = "paymentSuccessFallback")
    @Override
    public PaymentResponse paymentSuccess(Long id) {

        Payment payment = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Payment Not Found"));

        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        repository.save(payment);

        orderClient.updatePaymentStatus(
                payment.getOrderId(),
                "PAID");

        return mapper.toResponse(payment);
    }

// =========================================
// Payment Failed
// =========================================

    @CircuitBreaker(
            name = "orderService",
            fallbackMethod = "paymentFailedFallback")
    @Override
    public PaymentResponse paymentFailed(Long id) {

        Payment payment = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Payment Not Found"));

        payment.setPaymentStatus(PaymentStatus.FAILED);

        repository.save(payment);

        orderClient.updatePaymentStatus(
                payment.getOrderId(),
                "FAILED");

        return mapper.toResponse(payment);
    }

// =========================================
// Refund Payment
// =========================================

    @Override
    public PaymentResponse refundPayment(Long id) {

        Payment payment = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Payment Not Found"));

        payment.setPaymentStatus(PaymentStatus.REFUNDED);

        repository.save(payment);

        return mapper.toResponse(payment);
    }

// =========================================
// Verify Payment
// =========================================

    @Override
    public PaymentResponse verifyPayment(String transactionId) {

        Payment payment = repository
                .findByTransactionId(transactionId)
                .orElseThrow(() ->
                        new BusinessException("Transaction Not Found"));

        return mapper.toResponse(payment);
    }

// =========================================
// Fallback Methods
// =========================================

    public PaymentResponse paymentFallback(
            PaymentRequest request,
            Exception ex) {

        throw new BusinessException(
                "Order Service is unavailable. Please try again later.");
    }

    public PaymentResponse paymentSuccessFallback(
            Long id,
            Exception ex) {

        throw new BusinessException(
                "Payment completed but Order Service is unavailable.");
    }

    public PaymentResponse paymentFailedFallback(
            Long id,
            Exception ex) {

        throw new BusinessException(
                "Failed to update Order Service.");
    }
}
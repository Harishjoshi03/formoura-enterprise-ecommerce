
package com.formoura.payment.serviceImp;

import com.formoura.event.notification.InvoiceEvent;
import com.formoura.exception.exception.BusinessException;
import com.formoura.payment.client.OrderClient;
import com.formoura.payment.client.dto.OrderResponse;
import com.formoura.payment.dto.request.CreatePaymentRequest;
import com.formoura.payment.dto.request.PaymentRequest;
import com.formoura.payment.dto.request.VerifyPaymentRequest;
import com.formoura.payment.dto.response.PaymentResponse;
import com.formoura.payment.entity.Payment;
import com.formoura.payment.entity.PaymentStatus;
import com.formoura.payment.kafka.PaymentEventProducer;
import com.formoura.payment.mapper.PaymentMapper;
import com.formoura.payment.repository.PaymentRepository;
import com.formoura.payment.service.PaymentService;
import com.formoura.payment.util.InvoiceGenerator;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.razorpay.Utils;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    private final PaymentMapper mapper;

    private final OrderClient orderClient;

    private final RazorpayClient razorpayClient;

    private final InvoiceGenerator invoiceGenerator;

    private final PaymentEventProducer paymentEventProducer;

    @Value("${razorpay.key-id}")
    private String keyId;

    @Value("${razorpay.key-secret}")
    private String keySecret;

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

        String invoicePath =
                invoiceGenerator.generateInvoice(payment);

        orderClient.updatePaymentStatus(
                payment.getOrderId(),
                "PAID");
        InvoiceEvent event = InvoiceEvent.builder()
                            .orderId(payment.getOrderId())
                            .userId(payment.getUserId())
                            .email("harishjoshi579@gmail.com")
                            .invoicePath(invoicePath)
                            .build();
        paymentEventProducer.publishInvoice(event);

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

    @Override
    public PaymentResponse createPayment(
            CreatePaymentRequest request) {

        try {

            JSONObject options = new JSONObject();

            options.put(
                    "amount",
                    request.getAmount().multiply(
                            java.math.BigDecimal.valueOf(100)));

            options.put(
                    "currency",
                    "INR");

            options.put(
                    "receipt",
                    "order_" + request.getOrderId());

            Order order =
                    razorpayClient.orders.create(options);

            Payment payment = Payment.builder()

                    .orderId(request.getOrderId())

                    .userId(request.getUserId())

                    .amount(request.getAmount())

                    .paymentMethod(request.getPaymentMethod())

                    .paymentStatus(PaymentStatus.PENDING)

                    .gatewayOrderId(
                            order.get("id"))

                    .createdAt(LocalDateTime.now())

                    .updatedAt(LocalDateTime.now())

                    .build();

            repository.save(payment);

            return PaymentResponse.builder()

                    .paymentId(payment.getId().toString())

                    .razorpayOrderId(
                            payment.getGatewayOrderId())

                    .key(keyId)

                    .build();

        } catch (Exception ex) {

            throw new RuntimeException(ex);

        }

    }

    @Override
    public PaymentResponse verifyPayment(
            VerifyPaymentRequest request) {

        try {

            Payment payment = repository

                    .findByGatewayOrderId(
                            request.getRazorpayOrderId())

                    .orElseThrow(() ->
                            new BusinessException(
                                    "Payment Not Found"));

            Map<String,String> attributes =
                    new HashMap<>();

            attributes.put(
                    "razorpay_order_id",
                    request.getRazorpayOrderId());

            attributes.put(
                    "razorpay_payment_id",
                    request.getRazorpayPaymentId());

            attributes.put(
                    "razorpay_signature",
                    request.getRazorpaySignature());

            boolean verified =
                    Utils.verifyPaymentSignature(
                            (JSONObject) attributes,
                            keySecret);

            if(!verified){

                payment.setPaymentStatus(
                        PaymentStatus.FAILED);

                repository.save(payment);

                throw new BusinessException(
                        "Invalid Signature");

            }

            payment.setGatewayPaymentId(
                    request.getRazorpayPaymentId());

            payment.setGatewaySignature(
                    request.getRazorpaySignature());

            payment.setPaymentStatus(
                    PaymentStatus.SUCCESS);

            repository.save(payment);

            return PaymentResponse.builder()

                    .paymentId(payment.getId().toString())

                    .razorpayOrderId(
                            payment.getGatewayOrderId())

                    .razorpayPaymentId(
                            payment.getGatewayPaymentId())

                    .paymentStatus(PaymentStatus.SUCCESS)

                    .build();

        }
        catch (Exception ex){

            throw new RuntimeException(ex);

        }

    }
}
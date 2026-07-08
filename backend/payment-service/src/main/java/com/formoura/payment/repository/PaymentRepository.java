package com.formoura.payment.repository;

import com.formoura.payment.entity.Payment;
import com.formoura.payment.entity.PaymentGateway;
import com.formoura.payment.entity.PaymentMethod;
import com.formoura.payment.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentId(String paymentId);

    Optional<Payment> findByTransactionId(String transactionId);

    List<Payment> findByOrderId(Long orderId);

    List<Payment> findByUserId(Long userId);

    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);

    List<Payment> findByPaymentMethod(PaymentMethod paymentMethod);

    List<Payment> findByPaymentGateway(PaymentGateway paymentGateway);

    List<Payment> findByUserIdAndPaymentStatus(Long userId,
                                               PaymentStatus paymentStatus);

    List<Payment> findByOrderIdAndPaymentStatus(Long orderId,
                                                PaymentStatus paymentStatus);

    List<Payment> findByPaymentTimeBetween(LocalDateTime startDate,
                                           LocalDateTime endDate);

    List<Payment> findByCreatedAtBetween(LocalDateTime startDate,
                                         LocalDateTime endDate);

    boolean existsByPaymentId(String paymentId);

    boolean existsByTransactionId(String transactionId);

    Optional<Payment> findByGatewayOrderId(String gatewayOrderId);
}
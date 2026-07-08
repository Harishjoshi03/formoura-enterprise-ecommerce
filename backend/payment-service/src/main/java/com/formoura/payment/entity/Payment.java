package com.formoura.payment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String paymentId;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private PaymentGateway paymentGateway;

    @Column(unique = true)
    private String transactionId;

    @Column(name = "gateway_order_id", unique = true)
    private String gatewayOrderId;

    @Column(name = "gateway_payment_id")
    private String gatewayPaymentId;

    @Column(name = "gateway_signature")
    private String gatewaySignature;

    @Builder.Default
    private String currency = "INR";

    private String remarks;

    private LocalDateTime paymentTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (paymentTime == null) {
            paymentTime = LocalDateTime.now();
        }

        if (paymentStatus == null) {
            paymentStatus = PaymentStatus.PENDING;
        }

        if (currency == null) {
            currency = "INR";
        }
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
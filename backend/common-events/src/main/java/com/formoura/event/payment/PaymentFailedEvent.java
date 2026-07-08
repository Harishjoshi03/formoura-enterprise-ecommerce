package com.formoura.event.payment;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentFailedEvent {

    private Long paymentId;

    private Long orderId;

    private Long userId;

    private BigDecimal amount;

    private String reason;

    private LocalDateTime createdAt;

}
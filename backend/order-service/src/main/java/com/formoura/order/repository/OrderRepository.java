package com.formoura.order.repository;

import com.formoura.order.entity.Order;
import com.formoura.order.entity.OrderStatus;
import com.formoura.order.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByUserIdAndStatus(Long userId,
                                      OrderStatus status);

    Optional<Order> findByOrderNumber(String orderNumber);

    boolean existsByOrderNumber(String orderNumber);

    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Order> findByStatusOrderByCreatedAtDesc(OrderStatus status);

    List<Order> findByPaymentStatus(PaymentStatus paymentStatus);

    List<Order> findByUserIdAndPaymentStatus(Long userId,
                                             PaymentStatus paymentStatus);

    List<Order> findByCreatedAtBetween(LocalDateTime start,
                                       LocalDateTime end);
}
package com.formoura.order.repository;

import com.formoura.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository
        extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(Long orderId);

    void deleteByOrderId(Long orderId);

    long countByOrderId(Long orderId);

    List<OrderItem> findByProductId(Long productId);

    boolean existsByOrderIdAndProductId(Long orderId,
                                        Long productId);

    long countByProductId(Long productId);

}
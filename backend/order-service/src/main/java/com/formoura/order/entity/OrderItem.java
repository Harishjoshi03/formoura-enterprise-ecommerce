package com.formoura.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================
    // Order Mapping
    // ==========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // ==========================
    // Product Details
    // ==========================

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    private String imageUrl;

    private String sku;

    // ==========================
    // Pricing
    // ==========================

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;

    @PrePersist
    @PreUpdate
    public void calculateTotalPrice() {

        if (price != null && quantity != null) {
            totalPrice = price.multiply(BigDecimal.valueOf(quantity));
        }

    }

}
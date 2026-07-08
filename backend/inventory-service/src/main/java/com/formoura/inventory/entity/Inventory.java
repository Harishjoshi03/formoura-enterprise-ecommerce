package com.formoura.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long productId;

    @Column(nullable = false)
    private Integer availableQuantity;

    @Builder.Default
    @Column(nullable = false)
    private Integer reservedQuantity = 0;

    @Builder.Default
    @Column(nullable = false)
    private Integer soldQuantity = 0;

    @Builder.Default
    @Column(nullable = false)
    private Integer returnedQuantity = 0;

    @Builder.Default
    @Column(nullable = false)
    private Integer damagedQuantity = 0;

    private Integer minimumStock;

    private String warehouse;

    @Builder.Default
    private Boolean active = true;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();

        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();
    }

}
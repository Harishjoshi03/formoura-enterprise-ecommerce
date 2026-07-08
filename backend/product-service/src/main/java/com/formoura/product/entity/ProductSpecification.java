package com.formoura.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="product_specifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSpecification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String specKey;

    private String specValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private Product product;

}
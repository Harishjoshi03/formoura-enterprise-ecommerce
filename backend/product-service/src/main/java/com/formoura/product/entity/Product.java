package com.formoura.product.entity;

import com.formoura.product.category.entity.Category;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    @Column(length = 5000)
    private String description;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private Integer quantity;

    private String sku;

    private String brand;

    private String imageUrl;

    private Double rating = 0.0;

    private Integer reviewCount = 0;

    private Boolean active = true;

    private Boolean featured = false;

    private Boolean trending = false;

    private Integer stock;

    @Enumerated(EnumType.STRING)
    private StockStatus stockStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ProductSpecification> specifications = new ArrayList<>();

    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ProductVariant> variants = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @PrePersist
    void create(){

        createdAt=LocalDateTime.now();

        updatedAt=LocalDateTime.now();

    }

    @PreUpdate
    void update(){

        updatedAt=LocalDateTime.now();

    }

}
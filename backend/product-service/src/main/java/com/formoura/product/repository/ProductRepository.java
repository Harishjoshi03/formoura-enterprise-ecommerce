package com.formoura.product.repository;

import com.formoura.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategory_Id(Long categoryId,
                                     Pageable pageable);;

    Page<Product> findByProductNameContainingIgnoreCase(
            String keyword,
            Pageable pageable);



}
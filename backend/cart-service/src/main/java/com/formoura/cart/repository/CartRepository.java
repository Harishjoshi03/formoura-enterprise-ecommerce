package com.formoura.cart.repository;

import com.formoura.cart.entity.Cart;
import com.formoura.cart.entity.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserIdAndStatus(
            Long userId,
            CartStatus status);
    Optional<Cart> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    void deleteByUserId(Long userId);



}
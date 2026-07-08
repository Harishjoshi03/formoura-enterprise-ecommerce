package com.formoura.cart.repository;

import com.formoura.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartIdAndProductId(
            Long cartId,
            Long productId);
    List<CartItem> findByCartId(Long cartId);


    boolean existsByCartIdAndProductId(Long cartId,
                                       Long productId);

    void deleteByCartId(Long cartId);

    void deleteByCartIdAndProductId(Long cartId,
                                    Long productId);

    long countByCartId(Long cartId);



}
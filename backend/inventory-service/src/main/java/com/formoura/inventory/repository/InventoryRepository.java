package com.formoura.inventory.repository;

import com.formoura.inventory.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductId(Long productId);

    boolean existsByProductId(Long productId);

    Page<Inventory> findByActiveTrue(Pageable pageable);

    Page<Inventory> findByAvailableQuantityLessThanEqual(Integer quantity,
                                                         Pageable pageable);

    Page<Inventory> findByWarehouseContainingIgnoreCase(String warehouse,
                                                        Pageable pageable);

}
package com.formoura.product.controller;

import com.formoura.product.dto.request.ProductRequest;
import com.formoura.product.dto.response.ProductResponse;
import com.formoura.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products API", description = "Products Management APIs")
public class ProductController {

    private final ProductService service;

    // =========================
    // Create Product
    // =========================

    @Operation(summary = "Create Product")
    @PostMapping
    @PreAuthorize("@roleChecker.isAdminOrSeller()")
    public ProductResponse createProduct(
            @Valid @RequestBody ProductRequest request) {

        return service.createProduct(request);
    }

    // =========================
    // Update Product
    // =========================

    @Operation(summary = " Update Product")
    @PutMapping("/{id}")
    @PreAuthorize("@productAuthorizationService.isOwner(#productId)")
    public ProductResponse updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        return service.updateProduct(id, request);
    }

    // =========================
    // Get Product By Id
    // =========================

    @Operation(summary = " Get Product By Id")
    @GetMapping("/{id}")
    public ProductResponse getProduct(
            @PathVariable Long id) {

        return service.getProduct(id);
    }

    // =========================
    // Delete Product
    // =========================

    @Operation(summary = " Delete Product")
    @DeleteMapping("/{id}")
    @PreAuthorize("@roleChecker.isAdmin()")
    public void deleteProduct(
            @PathVariable Long id) {

        service.deleteProduct(id);
    }

    // =========================
    // Get All Products
    // =========================

    @Operation(summary = " Get All Products")
    @GetMapping
    public Page<ProductResponse> getAllProducts(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy) {

        return service.getAllProducts(page, size, sortBy);
    }

    // =========================
    // Search Product
    // =========================

    @Operation(summary = " Search Product")
    @GetMapping("/search")
    public Page<ProductResponse> searchProducts(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        return service.searchProducts(
                keyword,
                page,
                size);
    }

    // =========================
    // Products By Category
    // =========================

    @Operation(summary = " Products By Category")
    @GetMapping("/category/{categoryId}")
    public Page<ProductResponse> getProductsByCategory(

            @PathVariable Long categoryId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        return service.getProductsByCategory(
                categoryId,
                page,
                size);
    }

}
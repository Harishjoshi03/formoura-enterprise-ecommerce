package com.formoura.product.service;

import com.formoura.product.dto.request.ProductRequest;
import com.formoura.product.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(Long id,
                                  ProductRequest request);

    ProductResponse getProduct(Long id);

    void deleteProduct(Long id);

    Page<ProductResponse> getAllProducts(int page,
                                         int size,
                                         String sortBy);

    Page<ProductResponse> searchProducts(String keyword,
                                         int page,
                                         int size);

    Page<ProductResponse> getProductsByCategory(Long categoryId,
                                                int page,
                                                int size);



}

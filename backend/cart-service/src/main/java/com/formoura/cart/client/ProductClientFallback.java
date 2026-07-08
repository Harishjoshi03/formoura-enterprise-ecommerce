package com.formoura.cart.client;

import com.formoura.cart.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductClientFallback implements ProductClient {

    @Override
    public ProductDto getProduct(Long id) {
        throw new RuntimeException("Product Service is currently unavailable.");
    }
}
package com.formoura.cart.client;

import com.formoura.cart.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "product-service",
        fallback = ProductClientFallback.class
)
public interface ProductClient {

    @GetMapping("/api/v1/products/{id}")
    ProductDto getProduct(@PathVariable Long id);

}
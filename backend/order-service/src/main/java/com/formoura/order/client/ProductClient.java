package com.formoura.order.client;

import com.formoura.order.dto.client.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    ProductResponse getProductById(
            @PathVariable Long id);

}
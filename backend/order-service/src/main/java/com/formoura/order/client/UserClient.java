package com.formoura.order.client;

import com.formoura.order.dto.client.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users/{id}")
    UserResponse getUser(
            @PathVariable Long id);

}
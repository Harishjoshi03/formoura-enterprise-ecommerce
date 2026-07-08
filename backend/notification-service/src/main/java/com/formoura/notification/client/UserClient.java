package com.formoura.notification.client;

import com.formoura.notification.dto.response.UserResponse;
import com.formoura.notification.fallback.UserClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user-service",
        fallback = UserClientFallback.class
)
public interface UserClient {

    @GetMapping("/api/users/{id}")
    UserResponse getUserById(
            @PathVariable Long id);

}
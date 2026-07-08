package com.formoura.notification.fallback;

import com.formoura.notification.client.UserClient;
import com.formoura.notification.dto.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallback
        implements UserClient {

    @Override
    public UserResponse getUserById(Long id) {

        UserResponse response = new UserResponse();

        response.setId(id);

        response.setFirstName("Service");

        response.setLastName("Unavailable");

        response.setEmail("unknown@formoura.com");

        return response;
    }

}
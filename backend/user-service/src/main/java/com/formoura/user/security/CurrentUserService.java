package com.formoura.user.security;

import com.formoura.security.model.UserContext;
import com.formoura.security.util.UserContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserContextUtil userContextUtil;

    public Long getUserId() {

        UserContext user = userContextUtil.getCurrentUser();

        if (user == null) {
            throw new RuntimeException("Unauthorized");
        }

        return user.getUserId();
    }

    public String getEmail() {

        UserContext user = userContextUtil.getCurrentUser();

        if (user == null) {
            throw new RuntimeException("Unauthorized");
        }

        return user.getEmail();
    }

    public String getRole() {

        UserContext user = userContextUtil.getCurrentUser();

        if (user == null) {
            throw new RuntimeException("Unauthorized");
        }

        return user.getRole().name();
    }

}
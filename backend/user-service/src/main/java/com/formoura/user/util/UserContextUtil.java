/*

package com.formoura.user.util;

import com.formoura.user.config.UserContext;
import com.formoura.user.entity.Role;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class UserContextUtil {

    public UserContext getCurrentUser() {

        ServletRequestAttributes attributes =
                (ServletRequestAttributes)
                        RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            return null;
        }

        HttpServletRequest request = attributes.getRequest();

        String userId = request.getHeader("X-User-Id");

        String email = request.getHeader("X-Email");

        String role = request.getHeader("X-Role");

        if (userId == null) {

            return null;

        }

        return UserContext.builder()

                .userId(Long.valueOf(userId))

                .email(email)

                .role(Role.valueOf(role))

                .build();

    }

    public boolean isAdmin() {

        UserContext user = getCurrentUser();

        return user != null &&
                user.getRole() == Role.ADMIN;

    }

    public boolean isSuperAdmin() {

        UserContext user = getCurrentUser();

        return user != null &&
                user.getRole() == Role.SUPER_ADMIN;

    }

    public boolean isSeller() {

        UserContext user = getCurrentUser();

        return user != null &&
                user.getRole() == Role.SELLER;

    }

    public boolean isUser() {

        UserContext user = getCurrentUser();

        return user != null &&
                user.getRole() == Role.USER;

    }

}
*/

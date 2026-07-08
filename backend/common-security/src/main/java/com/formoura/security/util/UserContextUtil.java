package com.formoura.security.util;

import com.formoura.security.model.Role;
import com.formoura.security.model.UserContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class UserContextUtil {

    private static final String USER_ID = "X-User-Id";
    private static final String EMAIL = "X-Email";
    private static final String ROLE = "X-Role";

    public UserContext getCurrentUser() {

        ServletRequestAttributes attributes =
                (ServletRequestAttributes)
                        RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            return null;
        }

        HttpServletRequest request = attributes.getRequest();

        String userId = request.getHeader(USER_ID);
        String email = request.getHeader(EMAIL);
        String role = request.getHeader(ROLE);

        if (userId == null || role == null) {
            return null;
        }

        return UserContext.builder()
                .userId(Long.parseLong(userId))
                .email(email)
                .role(Role.ROLE_CUSTOMER)
                .build();
    }

    public Long getCurrentUserId() {

        UserContext user = getCurrentUser();

        return user != null ? user.getUserId() : null;
    }

    public String getCurrentUserEmail() {

        UserContext user = getCurrentUser();

        return user != null ? user.getEmail() : null;
    }

    public String getCurrentUserRole() {

        UserContext user = getCurrentUser();

        return user != null ? user.getRole().toString() : null;
    }

    public boolean hasRole(String role) {

        UserContext user = getCurrentUser();

        return user != null &&
                role.equalsIgnoreCase(user.getRole().toString());
    }

    public boolean isAdmin() {
        return hasRole("ADMIN");
    }

    public boolean isSuperAdmin() {
        return hasRole("SUPER_ADMIN");
    }

    public boolean isSeller() {
        return hasRole("SELLER");
    }

    public boolean isUser() {
        return hasRole("USER");
    }

    public boolean isAuthenticated() {
        return getCurrentUser() != null;
    }

   /* public UserContext getCurrentUser() {

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

                .role(BeanDefinitionDsl.Role.valueOf(role))

                .build();

    }*/
}
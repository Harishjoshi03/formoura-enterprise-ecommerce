package com.formoura.security.filter;

import com.formoura.security.model.UserContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;

@Component
public class UserContextFilter{

    public static final String USER_CONTEXT = "USER_CONTEXT";

    /**
     * Get logged-in user from ServerWebExchange
     */
    public UserContext getUser(ServerWebExchange exchange) {

        return exchange.getAttribute(USER_CONTEXT);

    }

    /**
     * Check ADMIN role
     */
    public boolean isAdmin(UserContext user) {

        return user != null
                && "ADMIN".equalsIgnoreCase(user.getRole());

    }

    /**
     * Check SUPER_ADMIN role
     */
    public boolean isSuperAdmin(UserContext user) {

        return user != null
                && "SUPER_ADMIN".equalsIgnoreCase(user.getRole());

    }

    /**
     * Check SELLER role
     */
    public boolean isSeller(UserContext user) {

        return user != null
                && "SELLER".equalsIgnoreCase(user.getRole());

    }

    /**
     * Check CUSTOMER role
     */
    public boolean isCustomer(UserContext user) {

        return user != null
                && "CUSTOMER".equalsIgnoreCase(user.getRole());

    }

    /**
     * Check DELIVERY_PARTNER role
     */
    public boolean isDeliveryPartner(UserContext user) {

        return user != null
                && "DELIVERY_PARTNER".equalsIgnoreCase(user.getRole());

    }

    /**
     * Generic role checker
     */
    public boolean hasRole(UserContext user, String role) {

        return user != null
                && role != null
                && role.equalsIgnoreCase(user.getRole());

    }

}
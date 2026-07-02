package com.formoura.security;

import com.formoura.security.model.UserContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class UserContextUtil {

    public static final String USER_CONTEXT = "USER_CONTEXT";

    public UserContext getUser(ServerWebExchange exchange) {

        return exchange.getAttribute(USER_CONTEXT);

    }

    public boolean isAdmin(UserContext user) {

        return user != null &&
                "ADMIN".equalsIgnoreCase(user.getRole());

    }

    public boolean isSuperAdmin(UserContext user) {

        return user != null &&
                "SUPER_ADMIN".equalsIgnoreCase(user.getRole());

    }

    public boolean isSeller(UserContext user) {

        return user != null &&
                "SELLER".equalsIgnoreCase(user.getRole());

    }

}
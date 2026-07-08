package com.formoura.security.security;

import com.formoura.security.util.UserContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("roleChecker")
@RequiredArgsConstructor
public class RoleChecker {

    private final UserContextUtil userContextUtil;

    public boolean isAdmin() {

        return userContextUtil.isAdmin();

    }

    public boolean isSeller() {

        return userContextUtil.isSeller();

    }

    public boolean isUser() {

        return userContextUtil.isUser();

    }

    public boolean isSuperAdmin() {

        return userContextUtil.isSuperAdmin();

    }

    public boolean isAdminOrSeller() {

        return isAdmin() || isSeller();

    }

    public boolean isAdminOrSuperAdmin() {

        return isAdmin() || isSuperAdmin();

    }

}
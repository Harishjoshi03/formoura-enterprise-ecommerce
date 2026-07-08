package com.formoura.user.security;

import com.formoura.security.model.Role;
import com.formoura.security.util.UserContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UserContextUtil userContextUtil;

    public boolean isOwner(Long userId) {

        return userContextUtil
                .getCurrentUser()
                .getUserId()
                .equals(userId);

    }

    public boolean isAdmin() {

        return userContextUtil
                .getCurrentUser()
                .getRole() == Role.ADMIN;

    }

    public boolean isSuperAdmin() {

        return userContextUtil
                .getCurrentUser()
                .getRole() == Role.SUPER_ADMIN;

    }

    public boolean isAdminOrOwner(Long userId) {

        return isOwner(userId)
                || isAdmin()
                || isSuperAdmin();

    }

}
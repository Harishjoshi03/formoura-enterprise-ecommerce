package com.formoura.user.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("@authorizationService.isAdminOrOwner(#id)")
public @interface IsAdminOrOwner {
}
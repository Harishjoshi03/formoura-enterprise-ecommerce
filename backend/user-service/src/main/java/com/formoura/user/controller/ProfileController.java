package com.formoura.user.controller;

import com.formoura.security.model.UserContext;
import com.formoura.security.util.UserContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final UserContextUtil userContextUtil;

    @GetMapping("/me")
    public UserContext me(){

        return userContextUtil.getCurrentUser();

    }

}
package com.formoura.user.controller;

import com.formoura.exception.exception.BusinessException;
import com.formoura.user.dto.request.*;
import com.formoura.user.dto.response.UserResponse;
import com.formoura.user.entity.Role;
import com.formoura.user.entity.UserStatus;
import com.formoura.user.mapper.UserMapper;
import com.formoura.user.repository.UserRepository;
import com.formoura.user.security.annotation.IsAdminOrOwner;
import com.formoura.user.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    private final UserMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "Bearer Authentication")
    public UserResponse createUser(
            @Valid @RequestBody CreateUserRequest request){

        return userService.createUser(request);

    }


    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    @IsAdminOrOwner
    public UserResponse getUser(
            @PathVariable Long id){

        return userService.getUser(id);

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public List<UserResponse> getAllUsers(){

        return userService.getAllUsers();

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}")
    @IsAdminOrOwner
    public UserResponse updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request){

        return userService.updateUser(id,request);

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    @PreAuthorize("@authorizationService.isSuperAdmin()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(
            @PathVariable Long id){

        userService.deleteUser(id);

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PatchMapping("/{id}/role")
    public void changeRole(
            @PathVariable Long id,
            @RequestBody ChangeRoleRequest request){

        userService.changeRole(id,request);

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PatchMapping("/{id}/password")
    public void changePassword(
            @PathVariable Long id,
            @Valid @RequestBody ChangePasswordRequest request){

        userService.changePassword(id,request);

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PatchMapping("/{id}/enable")
    public void enableUser(
            @PathVariable Long id){

        userService.enableUser(id);

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PatchMapping("/{id}/disable")
    public void disableUser(
            @PathVariable Long id){

        userService.disableUser(id);

    }


    @GetMapping("/page")
    public Page<UserResponse> getUsers(

            @RequestParam(defaultValue="0")
            int page,

            @RequestParam(defaultValue="10")
            int size){

        return userService.getUsers(page,size);

    }

    @GetMapping("/sort")

    public List<UserResponse> sort(

            @RequestParam(defaultValue="name")
            String field){

        return userRepository.findAll(

                        Sort.by(field))

                .stream()

                .map(mapper::toResponse)

                .toList();

    }

    @GetMapping("/email")

    public UserResponse byEmail(

            @RequestParam String email){

        return mapper.toResponse(

                userRepository.findByEmail(email)

                        .orElseThrow(

                                ()->new BusinessException(
                                        "User Not Found")));

    }

    @GetMapping("/role/{role}")

    public List<UserResponse> role(

            @PathVariable Role role){

        return userRepository.findByRole(role)

                .stream()

                .map(mapper::toResponse)

                .toList();

    }

    @GetMapping("/status/{status}")

    public List<UserResponse> status(

            @PathVariable UserStatus status){

        return userRepository.findByStatus(status)

                .stream()

                .map(mapper::toResponse)

                .toList();

    }

    @RestController
    @RequestMapping("/health")
    public class HealthController {

        @GetMapping
        public String health(){

            return "User Service Running";

        }

    }

    @GetMapping("/{userId}")
    public UserResponse getProfile(@PathVariable Long userId){

        return userService.getProfile(userId);

    }

    @PutMapping("/{userId}")
    public UserResponse updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateProfileRequest request){

        return userService.updateProfile(userId,request);

    }


}
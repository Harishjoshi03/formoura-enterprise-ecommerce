package com.formoura.user.service;

import com.formoura.user.dto.request.*;
import com.formoura.user.dto.response.UserResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse getUser(Long id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);

    void changeRole(Long id, ChangeRoleRequest request);

    void changePassword(Long id,
                        ChangePasswordRequest request);

    void enableUser(Long id);

    void disableUser(Long id);

    Page<UserResponse> getUsers(int page,
                                int size);

    UserResponse getProfile(Long userId);

    UserResponse updateProfile(Long userId,
                               UpdateProfileRequest request);




}
package com.formoura.user.mapper;

import com.formoura.user.dto.request.CreateUserRequest;
import com.formoura.user.dto.response.UserResponse;
import com.formoura.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(CreateUserRequest request);

    UserResponse toResponse(User user);

}
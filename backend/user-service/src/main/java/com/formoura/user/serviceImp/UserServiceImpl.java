package com.formoura.user.serviceImp;

import com.formoura.event.user.UserRegisteredEvent;
import com.formoura.exception.exception.BusinessException;
import com.formoura.user.dto.request.*;
import com.formoura.user.dto.response.UserResponse;
import com.formoura.user.entity.User;
import com.formoura.user.kafka.UserEventProducer;
import com.formoura.user.mapper.UserMapper;
import com.formoura.user.repository.UserRepository;
import com.formoura.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;

    private final UserEventProducer userEventProducer;

  /*  @Override
    public UserResponse createUser(CreateUserRequest request) {

        if(repository.existsByEmail(request.getEmail())){

            throw new BusinessException("Email already exists");

        }

        if(repository.existsByPhone(request.getPhone())){

            throw new BusinessException("Phone already exists");

        }

        User user=mapper.toEntity(request);

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()));

        return mapper.toResponse(
                repository.save(user));

    }*/

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        if (repository.existsByEmail(request.getEmail())) {

            throw new BusinessException("Email already exists");

        }

        if (repository.existsByPhone(request.getPhone())) {

            throw new BusinessException("Phone already exists");

        }

        User user = mapper.toEntity(request);

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()));

        // Save User
        User savedUser = repository.save(user);

        // Publish Kafka Event
        UserRegisteredEvent event = UserRegisteredEvent.builder()

                .userId(savedUser.getId())

                .fullName(savedUser.getName())   // agar entity me getName() hai to ye sahi hai

                .email(savedUser.getEmail())

                .role(savedUser.getRole().name())

                .registeredAt(LocalDateTime.now())

                .build();

        userEventProducer.publish(event);

        return mapper.toResponse(savedUser);

    }

    @Override
    public UserResponse getUser(Long id) {

        User user=repository.findById(id)
                .orElseThrow(()->
                        new BusinessException("User Not Found"));

        return mapper.toResponse(user);

    }

    @Override
    public List<UserResponse> getAllUsers() {

        return repository.findAll()

                .stream()

                .map(mapper::toResponse)

                .toList();

    }

    @Override
    public UserResponse updateUser(Long id,
                                   UpdateUserRequest request) {

        User user=repository.findById(id)

                .orElseThrow(()->
                        new BusinessException("User Not Found"));

        user.setName(request.getName());

        user.setPhone(request.getPhone());

        user.setEmail(request.getEmail());

        user.setProfileImage(request.getProfileImage());

        return mapper.toResponse(
                repository.save(user));

    }

    @Override
    public void deleteUser(Long id) {

        repository.deleteById(id);

    }

    @Override
    public void changeRole(Long id,
                           ChangeRoleRequest request) {

        User user=repository.findById(id)

                .orElseThrow(()->
                        new BusinessException("User Not Found"));

        user.setRole(request.getRole());

        repository.save(user);

    }

    @Override
    public void changePassword(Long userId,
                               ChangePasswordRequest request) {

        User user = repository.findById(userId)
                .orElseThrow(() ->
                        new BusinessException("User Not Found"));

        if (!passwordEncoder.matches(request.getOldPassword(),
                user.getPassword())) {

            throw new BusinessException("Old Password is Incorrect");

        }

        user.setPassword(
                passwordEncoder.encode(request.getNewPassword()));

        repository.save(user);

    }

    @Override
    public void enableUser(Long id) {

        User user=repository.findById(id)

                .orElseThrow(()->
                        new BusinessException("User Not Found"));

        user.setEnabled(true);

        repository.save(user);

    }

    @Override
    public void disableUser(Long id) {

        User user=repository.findById(id)

                .orElseThrow(()->
                        new BusinessException("User Not Found"));

        user.setEnabled(false);

        repository.save(user);

    }

    @Override
    public Page<UserResponse> getUsers(
            int page,
            int size){

        Pageable pageable=
                PageRequest.of(page,size);

        return repository.findAll(pageable)
                .map(mapper::toResponse);

    }

    @Override
    public UserResponse getProfile(Long userId) {

        User user = repository.findById(userId)
                .orElseThrow(() ->
                        new BusinessException("User Not Found"));

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .profileImage(user.getProfileImage())
                .build();

    }

    @Override
    public UserResponse updateProfile(Long userId,
                                      UpdateProfileRequest request) {

        User user = repository.findById(userId)
                .orElseThrow(() ->
                        new BusinessException("User Not Found"));

        user.setName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getMobile());
        user.setProfileImage(request.getProfileImage());

        repository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .profileImage(user.getProfileImage())
                .build();

    }

}
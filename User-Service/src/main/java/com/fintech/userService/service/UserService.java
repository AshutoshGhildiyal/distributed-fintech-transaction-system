package com.fintech.userService.service;

import com.fintech.userService.dto.request.CreateUserRequest;
import com.fintech.userService.dto.request.LoginRequest;
import com.fintech.userService.dto.response.LoginResponse;
import com.fintech.userService.dto.response.UserResponse;
import com.fintech.userService.model.UserEntity;

import java.util.Optional;

public interface UserService {

    UserEntity createUser(CreateUserRequest request);

    Optional<UserResponse> getUserById(Long id);

    UserEntity updateKycStatus(Long userId, String status);

    LoginResponse login(LoginRequest loginRequest);
}

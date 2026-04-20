package com.fintech.userService.service;

import com.fintech.userService.dto.request.CreateUserRequest;
import com.fintech.userService.dto.request.LoginRequest;
import com.fintech.userService.dto.response.LoginResponse;
import com.fintech.userService.dto.response.UserResponse;
import com.fintech.userService.model.UserEntity;
import com.fintech.userService.repository.UserRepository;
import com.fintech.userService.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements
        UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public UserEntity createUser(CreateUserRequest request) {

        UserEntity user = new UserEntity(request.getName(),  request.getEmail() , request.getPhone() ,
                encoder.encode(request.getPassword())
        );
        return userRepository.save(user);
    }

    @Override
    public Optional<UserResponse> getUserById(Long id) {
        Optional<UserEntity> user =  userRepository.findById(id);
        return Optional.of(mapToResponse(user.get()));
    }

    @Override
    public UserEntity updateKycStatus(Long userId, String status) {

        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found!!")
        );
        user.setKycStatus(status);

        return userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        UserEntity user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!encoder.matches( loginRequest.getPassword(), user.getPassword() )){
            throw new RuntimeException("Invalid email or password ");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUser(mapToResponse(user));

        return response;
    }

    private UserResponse mapToResponse(UserEntity user){

        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());

        return response;

    }
}

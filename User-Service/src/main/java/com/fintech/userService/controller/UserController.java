package com.fintech.userService.controller;

import com.fintech.userService.dto.request.CreateUserRequest;
import com.fintech.userService.dto.request.LoginRequest;
import com.fintech.userService.dto.request.UpdateKycRequest;
import com.fintech.userService.dto.response.ApiResponse;
import com.fintech.userService.dto.response.LoginResponse;
import com.fintech.userService.dto.response.UserResponse;
import com.fintech.userService.model.UserEntity;
import com.fintech.userService.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserEntity> createUser(@RequestBody CreateUserRequest request) {
        UserEntity user = userService.createUser(request);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return userService.getUserById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/kyc")
    public ResponseEntity<UserEntity> updateKyc(
            @PathVariable Long id,
            @RequestBody UpdateKycRequest request
    ) {
        UserEntity updatedUser = userService.updateKycStatus(id, request.getKycStatus());
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = userService.login(request);

        return ResponseEntity.ok(new ApiResponse<>(true, "Login Successfull", loginResponse));
    }
}

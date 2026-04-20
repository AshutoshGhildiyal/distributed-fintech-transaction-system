package com.fintech.userService.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    String token;
    private UserResponse user;
}

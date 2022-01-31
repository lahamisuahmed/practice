package com.practice.service;

import com.practice.dto.request.LoginRequest;
import com.practice.dto.response.LoginResponse;

public interface AuthenticationService {
    LoginResponse login(LoginRequest loginRequest);
}

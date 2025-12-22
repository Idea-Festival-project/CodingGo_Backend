package com.codinggo.auth.service;

import com.codinggo.auth.presentation.data.request.LoginRequest;
import com.codinggo.auth.presentation.data.response.AuthResponse;

public interface LoginService {
    AuthResponse execute(LoginRequest request);

    class UserService {
    }
}
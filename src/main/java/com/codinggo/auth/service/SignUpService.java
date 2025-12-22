package com.codinggo.auth.service;

import com.codinggo.auth.presentation.data.request.SignupRequest;

public interface SignUpService {
    void execute(SignupRequest request);
}
package com.codinggo.auth.service;

import com.codinggo.auth.presentation.data.request.PasswordResetRequest;

public interface PasswordResetService {
    void execute(PasswordResetRequest request);
}
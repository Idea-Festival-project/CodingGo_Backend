package com.codinggo.auth.service;

import com.codinggo.auth.presentation.data.request.PasswordUpdateRequest;

public interface PasswordUpdateService {
    void execute(PasswordUpdateRequest request);
}
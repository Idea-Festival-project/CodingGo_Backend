package com.codinggo.auth.service;

import com.codinggo.auth.presentation.data.request.ReissueRequest;
import com.codinggo.auth.presentation.data.response.AuthResponse;

public interface ReissueService {
    AuthResponse execute(ReissueRequest request);
}
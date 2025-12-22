package com.codinggo.auth.presentation.data.request;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class LoginRequest {
    private final String email;
    private final String password;
}
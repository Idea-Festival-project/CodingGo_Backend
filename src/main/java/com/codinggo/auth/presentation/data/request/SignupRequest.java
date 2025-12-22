package com.codinggo.auth.presentation.data.request;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class SignupRequest {
    private final String email;
    private final String username;
    private final String password;
}
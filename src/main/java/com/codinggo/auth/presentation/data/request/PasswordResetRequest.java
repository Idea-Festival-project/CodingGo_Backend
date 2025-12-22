package com.codinggo.auth.presentation.data.request;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class PasswordResetRequest {
    private final String email;
}
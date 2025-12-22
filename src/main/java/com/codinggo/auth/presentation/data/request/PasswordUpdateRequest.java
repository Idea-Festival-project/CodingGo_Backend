package com.codinggo.auth.presentation.data.request;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class PasswordUpdateRequest {
    private final String token;
    private final String newPassword;
}
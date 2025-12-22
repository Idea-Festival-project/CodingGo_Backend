package com.codinggo.auth.presentation.data.request;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class ReissueRequest {
    private final String refreshToken;
}
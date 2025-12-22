package com.codinggo.auth.presentation.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    @JsonProperty("accessToken")
    private String accessToken;

    @JsonProperty("refreshToken")
    private String refreshToken;

    @JsonProperty("accessTokenExpiresIn")
    private Long accessTokenExpiresIn;

    @JsonProperty("refreshTokenExpiresIn")
    private Long refreshTokenExpiresIn;
}
package com.codinggo.auth.service.impl;

import com.codinggo.auth.exception.AuthException;
import com.codinggo.auth.presentation.data.request.ReissueRequest;
import com.codinggo.auth.presentation.data.response.AuthResponse;
import com.codinggo.auth.service.ReissueService;
import Coding_GO.codingGO.global.exception.ErrorCode;
import Coding_GO.codingGO.global.exception.GlobalException;
import com.codinggo.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReissueServiceImpl implements ReissueService {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthResponse execute(ReissueRequest request) {
        Long userId = jwtTokenProvider.extractUserId(request.getRefreshToken());

        if (userId == null) {
            log.warn("Token reissue failed: Invalid refresh token");
            throw new GlobalException(ErrorCode.INVALID_TOKEN_EXCEPTION);
        }

        String newAccessToken = jwtTokenProvider.generateAccessToken(userId);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(userId);

        log.info("Token reissued for userId: {}", userId);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .accessTokenExpiresIn(jwtTokenProvider.getAccessTokenExpiration())
                .refreshTokenExpiresIn(jwtTokenProvider.getRefreshTokenExpiration())
                .build();
    }
}
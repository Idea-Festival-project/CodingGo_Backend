package com.codinggo.auth.service.impl;

import com.codinggo.auth.entity.UserEntity;
import com.codinggo.auth.exception.InvalidCredentialsException;
import com.codinggo.auth.exception.UserNotFoundException;
import com.codinggo.auth.presentation.data.request.LoginRequest;
import com.codinggo.auth.presentation.data.response.AuthResponse;
import com.codinggo.auth.repository.UserRepository;
import com.codinggo.global.security.JwtTokenProvider;
import com.codinggo.auth.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtProvider;

    @Override
    public AuthResponse execute(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("아이디 또는 비밀번호가 올바르지 않음"));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("아이디 또는 비밀번호가 올바르지 않음");
        }

        // JWT 토큰 생성
        String accessToken = jwtProvider.generateAccessToken(user.getUserId());
        String refreshToken = jwtProvider.generateRefreshToken(user.getUserId());

        log.info("User logged in successfully: {}", request.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(jwtProvider.getAccessTokenExpiration())
                .refreshTokenExpiresIn(jwtProvider.getRefreshTokenExpiration())
                .build();
    }
}
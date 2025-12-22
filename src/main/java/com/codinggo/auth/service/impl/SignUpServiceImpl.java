package com.codinggo.auth.service.impl;

import com.codinggo.auth.entity.UserEntity;
import com.codinggo.auth.presentation.data.request.SignupRequest;
import com.codinggo.auth.repository.UserRepository;
import com.codinggo.auth.service.SignUpService;
import Coding_GO.codingGO.global.exception.GlobalException;
import Coding_GO.codingGO.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void execute(SignupRequest request) {
        // 1차 쿼리: 이메일 또는 사용자명 중복 확인 (한 번의 쿼리)
        if (userRepository.existsByEmailOrUsername(request.getEmail(), request.getUsername())) {
            log.warn("회원가입 시도 - 이미 사용중인 이메일 또는 사용자명 - email: {}, username: {}",
                    request.getEmail(), request.getUsername());
            throw new GlobalException(ErrorCode.USER_ALREADY_EXISTS_EXCEPTION);
        }

        // 2. 사용자 생성
        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
        log.info("회원가입 성공 - email: {}, username: {}", request.getEmail(), request.getUsername());
    }
}
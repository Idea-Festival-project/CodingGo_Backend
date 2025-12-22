package com.codinggo.auth.service.impl;

import com.codinggo.auth.entity.UserEntity;
import com.codinggo.auth.exception.UserNotFoundException;
import com.codinggo.auth.presentation.data.request.PasswordResetRequest;
import com.codinggo.auth.repository.UserRepository;
import com.codinggo.auth.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final UserRepository userRepository;

    @Override
    public void execute(PasswordResetRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("해당하는 계정이 없음"));

        // 비밀번호 초기화 토큰 생성 및 전송 로직
        // 실제 구현에서는 이메일 전송 후 토큰 저장
        log.info("Password reset request for email: {}", request.getEmail());
    }
}
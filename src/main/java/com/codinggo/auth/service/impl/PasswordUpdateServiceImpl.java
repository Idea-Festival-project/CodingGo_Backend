package com.codinggo.auth.service.impl;

import com.codinggo.auth.exception.AuthException;
import com.codinggo.auth.exception.UserNotFoundException;
import com.codinggo.auth.presentation.data.request.PasswordUpdateRequest;
import com.codinggo.auth.repository.UserRepository;
import com.codinggo.auth.service.PasswordUpdateService;
import Coding_GO.codingGO.global.exception.ErrorCode;
import Coding_GO.codingGO.global.exception.GlobalException;
import com.codinggo.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PasswordUpdateServiceImpl implements PasswordUpdateService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void execute(PasswordUpdateRequest request) {
        Long userId = jwtTokenProvider.extractUserId(request.getToken());

        if (userId == null) {
            log.warn("Password update failed: Invalid token");
            throw new GlobalException(ErrorCode.INVALID_TOKEN_EXCEPTION);
        }

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND_EXCEPTION));

        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        log.info("Password updated for userId: {}", userId);
    }
}
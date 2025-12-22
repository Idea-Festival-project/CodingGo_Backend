package com.codinggo.auth.service.impl;

import com.codinggo.auth.entity.UserEntity;
import com.codinggo.auth.exception.UserAlreadyExistsException;
import com.codinggo.auth.presentation.data.request.SignupRequest;
import com.codinggo.auth.repository.UserRepository;
import com.codinggo.auth.service.SignUpService;
import Coding_GO.codingGO.global.exception.ErrorCode;
import Coding_GO.codingGO.global.exception.GlobalException;
import com.codinggo.profile.entity.ProfileEntity;
import com.codinggo.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void execute(SignupRequest request) {
        // 이메일 중복 체크
        // 이메일 또는 사용자명 중복 체크 (1번의 DB 쿼리)
        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Signup attempt with existing email: {}", request.getEmail());
            throw new GlobalException(ErrorCode.USER_ALREADY_EXISTS_EXCEPTION);
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("Signup attempt with existing username: {}", request.getUsername());
            throw new GlobalException(ErrorCode.USER_ALREADY_EXISTS_USERNAME);
        }

        // User 저장
        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        UserEntity savedUser = userRepository.save(user);

        // Profile 저장
        ProfileEntity profile = ProfileEntity.builder()
                .userId(savedUser.getUserId())
                .nickname(request.getUsername())
                .build();

        profileRepository.save(profile);

        log.info("User signed up successfully: {}", request.getEmail());
    }
}
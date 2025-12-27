package Coding_GO.codingGO.domain.user.service.impl;

import Coding_GO.codingGO.domain.user.entity.UserEntity;
import Coding_GO.codingGO.domain.user.presentation.data.LoginRequest;
import Coding_GO.codingGO.domain.user.presentation.data.SignupRequest;
import Coding_GO.codingGO.domain.user.presentation.data.TokenResponse;
import Coding_GO.codingGO.domain.user.repository.UserRepository;
import Coding_GO.codingGO.domain.user.service.AuthService;
import Coding_GO.codingGO.domain.user.service.EmailService;
import Coding_GO.codingGO.global.exception.ErrorCode;
import Coding_GO.codingGO.global.exception.GlobalException;
import Coding_GO.codingGO.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtProvider jwtProvider;
    private final StringRedisTemplate redisTemplate;

    @Override
    public void signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new GlobalException(ErrorCode.ALREADY_EXIST_EMAIL);
        }

        if (!emailService.verifyCode(request.email(), request.code())) {
            throw new GlobalException(ErrorCode.AUTHENTICATION_NUMBER_MISMATCH);
        }

        if(userRepository.existsByNickname(request.nickname())) {
            throw new GlobalException(ErrorCode.ALREADY_EXIST_NICKNAME);
        }

        userRepository.save(UserEntity.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .bojNickname(request.bojNickname())
                .build());

        emailService.deleteCode(request.email());
    }

    @Override
    public TokenResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new GlobalException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        String access = jwtProvider.createAccessToken(user.getEmail());
        String refresh = jwtProvider.createRefreshToken(user.getEmail());

        redisTemplate.opsForValue().set("REFRESH_TOKEN:" + user.getEmail(), refresh, Duration.ofDays(7));

        return new TokenResponse(access, refresh, "Bearer");
    }

    @Override
    public void logout(String accessToken) {
        String email = jwtProvider.getEmail(accessToken);

        redisTemplate.delete("REFRESH_TOKEN:" + email);

        long diff = jwtProvider.getExpiration(accessToken) - System.currentTimeMillis();
        if (diff > 0) {
            redisTemplate.opsForValue().set("BLACKLIST:" + accessToken, "logout", Duration.ofMillis(diff));
        }
    }
}
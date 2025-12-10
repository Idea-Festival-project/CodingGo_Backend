package Coding_GO.codingGO.domain.auth.service;

import Coding_GO.codingGO.domain.auth.dto.LoginRequest;
import Coding_GO.codingGO.domain.auth.dto.LoginResponse;
import Coding_GO.codingGO.domain.auth.dto.SignUpRequest;
import Coding_GO.codingGO.domain.auth.dto.UserResponse;
import Coding_GO.codingGO.domain.auth.entity.User;
import Coding_GO.codingGO.domain.auth.repository.UserRepository;
import Coding_GO.codingGO.domain.auth.service.mapper.UserMapper;
import Coding_GO.codingGO.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider; // JwtTokenProvider 추가

    @Override
    public UserResponse signUp(SignUpRequest request) {
        validateSignUp(request);
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = userMapper.toEntity(request, encodedPassword);
        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        // 1) 이메일로 사용자 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        // 2) 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3) JWT 토큰 생성
        String accessToken = jwtTokenProvider.createToken(user.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());

        // 4) 응답 반환
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void validateSignUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }
        if (request.getPassword() == null || request.getPassword().length() < 8) {
            throw new IllegalArgumentException("비밀번호는 8자 이상이어야 합니다.");
        }
    }
}

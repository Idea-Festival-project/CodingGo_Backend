package Coding_GO.codingGO.domain.auth.controller;

import Coding_GO.codingGO.domain.auth.dto.*;
import Coding_GO.codingGO.domain.auth.service.AuthService;
import Coding_GO.codingGO.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")  // 소문자로 변경
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 회원가입 API
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponse>> signup(@Valid @RequestBody SignUpRequest request) {
        UserResponse userResponse = authService.signUp(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of("회원가입이 완료되었습니다.", userResponse));
    }

    /**
     * 로그인 API
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.login(request);
        return ResponseEntity
                .ok(ApiResponse.of("로그인이 완료되었습니다.", loginResponse));
    }

    /**
     * 토큰 재발급 API (예정)
     */
    @PutMapping("/reissue")
    public ResponseEntity<ApiResponse<LoginResponse>> reissue(@RequestHeader("Refresh-Token") String refreshToken) {
        // TODO: 구현 예정
        return ResponseEntity
                .ok(ApiResponse.of("토큰 재발급 예정"));
    }

    /**
     * 로그아웃 API (예정)
     */
    @DeleteMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader("Authorization") String accessToken) {
        // TODO: 구현 예정
        return ResponseEntity
                .ok(ApiResponse.of("로그아웃 예정"));
    }

    /**
     * 비밀번호 변경 API (예정)
     */
    @PutMapping("/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @Valid @RequestBody PasswordChangeRequest request) {
        // TODO: 구현 예정
        return ResponseEntity
                .ok(ApiResponse.of("비밀번호 변경 예정"));
    }

    /**
     * 비밀번호 재설정 요청 API (예정)
     */
    @PostMapping("/password/request")
    public ResponseEntity<ApiResponse<Void>> requestPasswordReset(
            @Valid @RequestBody PasswordChangeEmailRequest request) {
        // TODO: 구현 예정
        return ResponseEntity
                .ok(ApiResponse.of("비밀번호 재설정 이메일 발송 예정"));
    }
}
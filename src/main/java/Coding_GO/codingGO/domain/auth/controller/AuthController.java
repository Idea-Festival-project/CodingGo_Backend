package Coding_GO.codingGO.domain.auth.controller;

import Coding_GO.codingGO.domain.auth.dto.LoginRequest;
import Coding_GO.codingGO.domain.auth.dto.LoginResponse;
import Coding_GO.codingGO.domain.auth.dto.SignUpRequest;
import Coding_GO.codingGO.domain.auth.dto.UserResponse;
import Coding_GO.codingGO.domain.auth.service.AuthService;
import Coding_GO.codingGO.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")  // /api/CodingGo/auth -> /api/auth 로 단순화
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 회원가입 API
     * @param request 회원가입 요청 정보 (이메일, 비밀번호, 닉네임)
     * @return 생성된 사용자 정보
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
     * @param request 로그인 요청 정보 (이메일, 비밀번호)
     * @return JWT Access Token과 Refresh Token
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.login(request);
        return ResponseEntity
                .ok(ApiResponse.of("로그인이 완료되었습니다.", loginResponse));
    }
}
package Coding_GO.codingGO.domain.auth.controller;

import Coding_GO.codingGO.domain.auth.dto.LoginRequest;
import Coding_GO.codingGO.domain.auth.dto.PasswordChangeEmailRequest;
import Coding_GO.codingGO.domain.auth.dto.PasswordChangeRequest;
import Coding_GO.codingGO.domain.auth.dto.SignupRequest;
import Coding_GO.codingGO.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/CodingGo/auth")
public class AuthController {

    // 회원가입: POST /api/CodingGo/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(new ApiResponse("signup OK"));
    }

    // 로그인: POST /api/CodingGo/auth/login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(new ApiResponse("login OK"));
    }

    // 토큰 재발급: PUT /api/CodingGo/auth/reissue
    @PutMapping("/reissue")
    public ResponseEntity<ApiResponse> reissue() {
        return ResponseEntity.ok(new ApiResponse("reissue OK"));
    }

    // 로그아웃: DELETE /api/CodingGo/auth/logout
    @DeleteMapping("/logout")
    public ResponseEntity<ApiResponse> logout() {
        return ResponseEntity.ok(new ApiResponse("logout OK"));
    }

    // 비밀번호 변경: PUT /api/CodingGo/auth/password
    @PutMapping("/password")
    public ResponseEntity<ApiResponse> changePassword(@RequestBody PasswordChangeRequest request) {
        return ResponseEntity.ok(new ApiResponse("password change OK"));
    }

    // 비밀번호 변경 요청: POST /api/CodingGo/auth/password/request
    @PostMapping("/password/request")
    public ResponseEntity<ApiResponse> requestPasswordChange(
            @RequestBody PasswordChangeEmailRequest request) {
        return ResponseEntity.ok(new ApiResponse("password change request OK"));
    }
}

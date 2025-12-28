package Coding_GO.codingGO.domain.user.presentation.controller;

import Coding_GO.codingGO.domain.user.presentation.data.LoginRequest;
import Coding_GO.codingGO.domain.user.presentation.data.SignupRequest;
import Coding_GO.codingGO.domain.user.presentation.data.TokenResponse;
import Coding_GO.codingGO.domain.user.service.AuthService;
import Coding_GO.codingGO.domain.user.service.EmailService;
import Coding_GO.codingGO.global.security.jwt.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth API", description = "인증 관련 API (회원가입, 로그인 등)")
@RestController
@RequestMapping("/api/CodingGo/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5175")
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;
    private final JwtProvider jwtProvider;

    @PostMapping("/email")
    @Operation(summary = "이메일 인증번호 발송", description = "입력한 이메일로 6자리 인증번호를 발송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증번호 발송 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 이메일 형식", content = @Content),
            @ApiResponse(responseCode = "500", description = "메일 발송 서버 에러", content = @Content)
    })
    public ResponseEntity<Void> sendEmail(@RequestParam String email) {
        emailService.sendCode(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "이메일 인증번호 확인 후 회원가입을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "인증번호 불일치 혹은 잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일 혹은 닉네임", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content)
    })
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인하여 JWT 토큰을 발급받습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공 및 토큰 발급", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "비밀번호 불일치", content = @Content),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content)
    })
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @DeleteMapping("/logout")
    @Operation(summary = "로그아웃", description = "현재 사용 중인 토큰을 무효화(블랙리스트) 처리합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패 (유효하지 않은 토큰)", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content)
    })
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        if (token != null) {
            authService.logout(token);
        }
        return ResponseEntity.noContent().build();
    }
}
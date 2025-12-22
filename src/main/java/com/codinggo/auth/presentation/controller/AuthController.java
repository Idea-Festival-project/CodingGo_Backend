package com.codinggo.auth.presentation.controller;

import com.codinggo.auth.presentation.data.request.*;
import com.codinggo.auth.presentation.data.response.AuthResponse;
import com.codinggo.auth.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/CodingGo/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SignUpService signUpService;
    private final LoginService loginService;
    private final LogoutService logoutService;
    private final ReissueService reissueService;
    private final PasswordResetService passwordResetService;
    private final PasswordUpdateService passwordUpdateService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupRequest request) {
        signUpService.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = loginService.execute(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody ReissueRequest request) {
        logoutService.execute(request.getRefreshToken());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reissue")
    public ResponseEntity<AuthResponse> reissue(@RequestBody ReissueRequest request) {
        AuthResponse response = reissueService.execute(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/password/request")
    public ResponseEntity<Void> requestPasswordReset(@RequestBody PasswordResetRequest request) {
        passwordResetService.execute(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody PasswordUpdateRequest request) {
        passwordUpdateService.execute(request);
        return ResponseEntity.ok().build();
    }
}
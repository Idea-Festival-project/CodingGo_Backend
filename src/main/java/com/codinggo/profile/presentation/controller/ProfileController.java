package com.codinggo.profile.presentation.controller;

import com.codinggo.profile.presentation.data.request.UpdateProfileRequest;
import com.codinggo.profile.presentation.data.response.ProfileResponse;
import com.codinggo.profile.presentation.data.response.ApiResponse;
import com.codinggo.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/CodingGo/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    /**
     * 내 프로필 조회
     * GET /api/CodingGo/profile/my
     */
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<ProfileResponse>> getMyProfile(
            @AuthenticationPrincipal Long userId
    ) {
        ProfileResponse profile = profileService.getProfile(userId);
        return ResponseEntity.ok(ApiResponse.success(profile));
    }

    /**
     * 다른 사용자 프로필 조회
     * GET /api/CodingGo/profile/{userId}
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<ProfileResponse>> getUserProfile(
            @PathVariable Long userId
    ) {
        ProfileResponse profile = profileService.getUserProfile(userId);
        return ResponseEntity.ok(ApiResponse.success(profile));
    }

    /**
     * 프로필 수정
     * PUT /api/CodingGo/profile/my
     */
    @PutMapping("/my")
    public ResponseEntity<ApiResponse<Void>> updateProfile(
            @AuthenticationPrincipal Long userId,
            @RequestBody UpdateProfileRequest request
    ) {
        profileService.updateProfile(userId, request);
        return ResponseEntity.ok(ApiResponse.success("프로필이 업데이트되었습니다."));
    }
}
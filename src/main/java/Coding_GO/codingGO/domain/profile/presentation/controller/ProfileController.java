package Coding_GO.codingGO.domain.profile.presentation.controller;

import Coding_GO.codingGO.domain.profile.presentation.data.response.ProfileResponse;
import Coding_GO.codingGO.domain.profile.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Profile API", description = "유저 프로필 조회 및 수정 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coding-go/profile")
public class ProfileController {


    private final UserProfileService userProfileService;

    @GetMapping("/my")
    @Operation(summary = "내 프로필 조회", description = "현재 로그인한 유저의 프로필 정보(닉네임, 소개, 이미지 등)를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "인증 자격 증명이 유효하지 않음"),
            @ApiResponse(responseCode = "404", description = "유저 정보를 찾을 수 없음")
    })
    public ResponseEntity<ProfileResponse> getProfile(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId
    ) {
        return ResponseEntity.ok(userProfileService.getProfileById(userId));
    }

    @GetMapping("/{nickname}")
    @Operation(summary = "타인 프로필 조회", description = "닉네임을 검색하여 해당 유저의 프로필 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 닉네임입니다.")
    })
    public ResponseEntity<ProfileResponse> searchProfile(
            @Parameter(description = "검색할 유저의 닉네임", example = "coding_master")
            @PathVariable String nickname
    ) {
        return ResponseEntity.ok(userProfileService.getProfileByNickname(nickname));
    }

    @PostMapping(value = "/my", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "내 프로필 수정", description = "자기소개(bio)와 프로필 이미지(image)를 수정합니다. 둘 다 선택 사항입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 파일 형식이거나 요청 오류"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    public ResponseEntity<String> updateProfile(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @Parameter(description = "수정할 자기소개 내용")
            @RequestPart(value = "bio", required = false) String bio,
            @Parameter(description = "업로드할 프로필 이미지 파일")
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        userProfileService.updateProfile(userId, bio, image);
        return ResponseEntity.ok("Success");
    }
}
package Coding_GO.codingGO.domain.community.presentation.controller;

import Coding_GO.codingGO.domain.community.presentation.data.request.CreateCommunityRequest;
import Coding_GO.codingGO.domain.community.presentation.data.request.UpdateCommunityRequest;
import Coding_GO.codingGO.domain.community.presentation.data.response.CreateCommunityResponse;
import Coding_GO.codingGO.domain.community.presentation.data.response.GetCommunityListResponse;
import Coding_GO.codingGO.domain.community.service.CreateCommunityService;
import Coding_GO.codingGO.domain.community.service.DeleteCommunityService;
import Coding_GO.codingGO.domain.community.service.GetCommunityService;
import Coding_GO.codingGO.domain.community.service.UpdateCommunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Community API" , description = "커뮤니티 관련 API")
@RequestMapping("api/CodingGo/community")
@RestController
@RequiredArgsConstructor

public class CommunityController {

    private final CreateCommunityService createCommunityService;
    private final GetCommunityService getCommunityService;
    private final UpdateCommunityService updateCommunityService;
    private final DeleteCommunityService deleteCommunityService;

    @PostMapping("/write")
    @Operation(summary = "커뮤니티 글 작성", description = "카테고리와 내용을 입력받아서 글을 작성합니다. 제목은 작성자 이름으로 들어갑니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "커뮤니티 글 작성 성공",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "405",
                    description = "커뮤니티 글 작성에 실패",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = " 권한 없음 ",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류 발생",
                    content = @Content
            )
    })
    public ResponseEntity<CreateCommunityResponse> createCommunity(
            @RequestBody CreateCommunityRequest request,
            @AuthenticationPrincipal Long userId) {
        CreateCommunityResponse community = createCommunityService.execute(request,userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(community);
    }

    @GetMapping
    @Operation(summary = "커뮤니티 글 전체 조회" , description = "커뮤니티 글 전체를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "커뮤니티 글 전체 조회 성공",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description =  "커뮤니티 글 조회 실패",
                    content = @Content
            ),
    })
    public ResponseEntity<GetCommunityListResponse> findAllCommunity(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @AuthenticationPrincipal Long userId
    ) {
        GetCommunityListResponse community = getCommunityService.execute(page,limit);
        return ResponseEntity.ok(community);
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "커뮤니티 글 수정", description = "커뮤니티 글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "커뮤니티 글 수정 성공",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403",
                    description =  "권한 없음",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description =  "커뮤니티 글 조회 실패",
                    content = @Content
            )
    })
    public ResponseEntity<CreateCommunityResponse> updateCommunity(
            @PathVariable Long postId,
            @RequestBody UpdateCommunityRequest request,
            @AuthenticationPrincipal Long userId
    ){
        CreateCommunityResponse response = updateCommunityService.execute(postId ,request,userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "커뮤니티 글 삭제", description = "커뮤니티 글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description =  "커뮤니티 글 삭제 성공",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403",
                    description =  "권한 없음",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description =  "커뮤니티 글 조회 실패",
                    content = @Content
            )
    })
    public ResponseEntity<Void> deleteCommunity(@PathVariable("post_id") Long postId) {
        deleteCommunityService.execute(postId);
        return ResponseEntity.noContent().build();
    }
}

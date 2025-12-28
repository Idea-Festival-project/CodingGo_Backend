package Coding_GO.codingGO.domain.community.presentation.controller;

import Coding_GO.codingGO.domain.community.presentation.data.request.comment.CreateCommentRequest;
import Coding_GO.codingGO.domain.community.presentation.data.request.comment.UpdateCommentRequest;
import Coding_GO.codingGO.domain.community.presentation.data.request.community.CreateCommunityRequest;
import Coding_GO.codingGO.domain.community.presentation.data.request.community.UpdateCommunityRequest;
import Coding_GO.codingGO.domain.community.presentation.data.response.comment.CreateCommentResponse;
import Coding_GO.codingGO.domain.community.presentation.data.response.comment.GetCommentResponse;
import Coding_GO.codingGO.domain.community.presentation.data.response.community.CreateCommunityResponse;
import Coding_GO.codingGO.domain.community.presentation.data.response.GetCommunityListResponse;
import Coding_GO.codingGO.domain.community.service.comment.CreateCommentService;
import Coding_GO.codingGO.domain.community.service.comment.DeleteCommentService;
import Coding_GO.codingGO.domain.community.service.comment.GetCommentService;
import Coding_GO.codingGO.domain.community.service.comment.UpdateCommentService;
import Coding_GO.codingGO.domain.community.service.community.CreateCommunityService;
import Coding_GO.codingGO.domain.community.service.community.DeleteCommunityService;
import Coding_GO.codingGO.domain.community.service.GetCommunityService;
import Coding_GO.codingGO.domain.community.service.community.UpdateCommunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Community API", description = "커뮤니티 관련 API")
@RequestMapping("api/CodingGo/community")
@RestController
@RequiredArgsConstructor

public class CommunityController {

    private final CreateCommunityService createCommunityService;
    private final GetCommunityService getCommunityService;
    private final UpdateCommunityService updateCommunityService;
    private final DeleteCommunityService deleteCommunityService;
    private final GetCommentService getCommentService;
    private final CreateCommentService createCommentService;
    private final UpdateCommentService updateCommentService;
    private final DeleteCommentService deleteCommentService;

    @PostMapping("/write")
    @Operation(summary = "커뮤니티 글 작성", description = "카테고리와 내용을 입력받아서 글을 작성합니다. 제목은 작성자 이름으로 들어갑니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "커뮤니티 글 작성 성공",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
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
        CreateCommunityResponse community = createCommunityService.execute(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(community);
    }

    @GetMapping
    @Operation(summary = "커뮤니티 글 전체 조회", description = "커뮤니티 글 전체를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "커뮤니티 글 전체 조회 성공",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "커뮤니티 글 조회 실패",
                    content = @Content
            ),
    })
    public ResponseEntity<GetCommunityListResponse> findAllCommunity(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        GetCommunityListResponse community = getCommunityService.execute(page, limit);
        return ResponseEntity.ok(community);
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "커뮤니티 글 수정", description = "커뮤니티 글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "커뮤니티 글 수정 성공",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "권한 없음",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 글을 조회",
                    content = @Content
            )
    })
    public ResponseEntity<CreateCommunityResponse> updateCommunity(
            @PathVariable Long postId,
            @RequestBody UpdateCommunityRequest request,
            @AuthenticationPrincipal Long userId
    ) {
        CreateCommunityResponse response = updateCommunityService.execute(postId, request, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "커뮤니티 글 삭제", description = "커뮤니티 글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "커뮤니티 글 삭제 성공",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "권한 없음",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "커뮤니티 글 조회 실패",
                    content = @Content
            )
    })
    public ResponseEntity<Void> deleteCommunity(@PathVariable("postId") Long postId) {
        deleteCommunityService.execute(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{post_id}/comments")
    @Operation(summary = "커뮤니티 댓글 조회", description = "선택한 해당 커뮤니티의 댓글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "커뮤니티 글 댓글 조회 성공",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "커뮤니티 글 댓글 조회 실패",
                    content = @Content
            )
    })
    public ResponseEntity<List<GetCommentResponse>> getComment(
            @PathVariable("post_id") Long postId
    ) {
        List<GetCommentResponse> response = getCommentService.execute(postId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{post_id}/comments")
    @Operation(summary = "커뮤니티 댓글 작성", description = "커뮤니티 글에 댓글을 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "커뮤니티 댓글 작성 성공",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "커뮤니티 댓글 작성에 실패",
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
    public ResponseEntity<CreateCommentResponse> createComment(
            @PathVariable("post_id") Long postId,
            @RequestBody CreateCommentRequest request,
            @AuthenticationPrincipal Long userId
    ) {
        CreateCommentResponse comment = createCommentService.execute(postId, userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @PatchMapping("/comments/{comment_id}")
    @Operation(summary = "커뮤니티 댓글 수정", description = "커뮤니티 댓글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "커뮤니티 댓글 수정 성공",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "권한 없음",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 글의 댓글을 조회",
                    content = @Content
            )
    })
    public ResponseEntity<CreateCommentResponse> updateComment(
            @PathVariable("comment_id") Long commentId,
            @RequestBody @Valid UpdateCommentRequest request,
            @AuthenticationPrincipal Long userId
    ) {
        CreateCommentResponse comment = updateCommentService.execute(commentId, request, userId);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/comments/{comment_id}")
    @Operation(summary = "커뮤니티 댓글 삭제", description = "커뮤니티 댓글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "커뮤니티 댓글 삭제 성공",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "권한 없음",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "커뮤니티 글 댓글 조회 실패",
                    content = @Content
            )
    })
    public ResponseEntity<Void> deleteComment(
            @PathVariable("comment_id") Long commentId,
            @AuthenticationPrincipal Long userId
    ) {
        deleteCommentService.execute(commentId, userId);
        return ResponseEntity.noContent().build();
    }
}

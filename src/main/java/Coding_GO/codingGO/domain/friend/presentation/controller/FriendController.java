package Coding_GO.codingGO.domain.friend.presentation.controller;


import Coding_GO.codingGO.domain.friend.presentation.data.request.SendFriendRequest;
import Coding_GO.codingGO.domain.friend.presentation.data.response.GetFriendListResponse;
import Coding_GO.codingGO.domain.friend.presentation.data.response.GetPendingFriendRequestResponse;
import Coding_GO.codingGO.domain.friend.presentation.data.response.SendFriendResponse;
import Coding_GO.codingGO.domain.friend.service.*;
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


@Tag(name = "Friend API", description = "친구 관련 API")
@RequestMapping("/api/CodingGo/friend")
@RestController
@RequiredArgsConstructor
public class FriendController {
    private final GetFriendListService getFriendListService; //내 친구 목록 조회
    private final SendFriendRequestService sendFriendRequestService; // 친구 요청 보내기
    private final AcceptFriendRequestService acceptFriendRequestService; // 친구 요청 수락
    private final RejectFriendRequestService rejectFriendRequestService; // 친구 요청 거절
    private final GetPendingFriendRequestService getPendingFrienddRequestService; //받은 친구 요청 목록 조회
    private final GetSentFriendRequestService getSentFriendRequestService; // 보낸 친구 요청 목록 조회
    private final DeleteFriendService deleteFriendService; // 친구 삭제

    @GetMapping
    @Operation(summary = "내 친구 목록 조회", description = "자신의 현재 친구 목록 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "자신의 친구 목록 조회 성공",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "자신의 친구 목록 조회 실패",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에러",
                    content = @Content
            )
    })
    public ResponseEntity<GetFriendListResponse> getFriendList(
            @AuthenticationPrincipal Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit

    ) {
        GetFriendListResponse friends = getFriendListService.execute(userId, page, limit);
        return ResponseEntity.ok(friends);
    }

    @PostMapping("/request")
    @Operation(summary = "친구 요청 보내기", description = "친구 요청을 보냅니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "친구 요청 성공",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청입니다.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증에 실패했습니다.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "이미 친구인 사용자 입니다.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 에러",
                    content = @Content
            )
    })
    public ResponseEntity<SendFriendResponse> sendFriendRequest(
            @Valid @RequestBody SendFriendRequest request,
            @AuthenticationPrincipal Long userId
    ) {
        SendFriendResponse response = sendFriendRequestService.execute(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/request/received/{friends_id}/accept")
    @Operation(summary = "친구 요청 수락", description = "친구 요청 수락합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "친구 요청 수락 성공",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "잘못된 요청",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "존재하지 않는 친구",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 오류",
                            content = @Content
                    )
            })
    public ResponseEntity<Void> acceptFriendRequest(
            @PathVariable Long friends_id,
            @AuthenticationPrincipal Long userId
    ) {
        acceptFriendRequestService.execute(friends_id, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/request/received/{friends_id}/reject")
    @Operation(summary = "친구 요청 거절", description = "친구 요청 거절합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "친구 요청 거절 성공",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "잘못된 요청",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "존재하지 않는 친구",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 오류",
                            content = @Content
                    )
            })
    public ResponseEntity<Void> rejectFriendRequest(
            @PathVariable Long friends_id,
            @AuthenticationPrincipal Long userId
    ) {
        rejectFriendRequestService.execute(friends_id, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{targetUser_id}")
    @Operation(summary = "친구 삭제", description = "친구를 삭제합니다")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "친구 삭제 성공",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "잘못된 요청",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "존재하지 않는 친구",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 오류",
                            content = @Content
                    )
            })
    public ResponseEntity<Void> deleteFriendRequest(
            @PathVariable Long targetUser_id,
            @AuthenticationPrincipal Long userId
    ) {
        deleteFriendService.execute(targetUser_id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/request/received")
    @Operation(summary = "받은 친구 요청 목록 조회", description = "받은 친구 요청 목록 조회합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "받은 친구 요청 목록 조회 성공",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 에러",
                            content = @Content
                    )
            })
    public ResponseEntity<GetPendingFriendRequestResponse> getPendingRequests(
            @AuthenticationPrincipal Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        GetPendingFriendRequestResponse response = getPendingFrienddRequestService.execute(userId, page, limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/request/sent")
    @Operation(summary = "보낸 친구 요청 목록 조회", description = "보낸 친구 요청 목록 조회합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "보낸 친구 요청 목록 조회 성공",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 에러",
                            content = @Content
                    )
            })
    public ResponseEntity<GetPendingFriendRequestResponse> getSentRequest(
            @AuthenticationPrincipal Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ){
        GetPendingFriendRequestResponse response =  getSentFriendRequestService.execute(userId,page,limit);
        return ResponseEntity.ok(response);
    }
}
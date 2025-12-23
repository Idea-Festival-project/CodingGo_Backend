package Coding_GO.codingGO.domain.problem.presentation.controller;

import Coding_GO.codingGO.domain.problem.presentation.data.response.ProblemResponse;
import Coding_GO.codingGO.domain.problem.service.ProblemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/CodingGo/problem")
@RequiredArgsConstructor
public class ProblemController {
    private final ProblemService problemService;

    @Operation(
            summary = "문제 목록 조회 및 실시간 포인트 정산",
            description = "유저가 접속할 때 백준 풀이 기록을 확인하여 포인트를 지급하고, 최신 문제 목록을 인기순으로 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 및 정산 성공",
                    content = @Content(schema = @Schema(implementation = ProblemResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (파라미터 오류)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "해당 유저를 찾을 수 없음",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 또는 외부 API(Solved.ac) 연동 오류",
                    content = @Content
            )
    })
    public ResponseEntity<Page<ProblemResponse>> getProblemList(
            @AuthenticationPrincipal Long userId,
            @RequestParam(defaultValue = "1000") int start,
            @RequestParam(defaultValue = "40000") int end,
            @PageableDefault(size = 20) Pageable pageable
    ){
        return ResponseEntity.ok(problemService.getProblemList(userId, start, end, pageable));
    }
}

package Coding_GO.codingGO.domain.aiQuiz.presentation.controller;

import Coding_GO.codingGO.domain.aiQuiz.presentation.data.request.AiResultRequest;
import Coding_GO.codingGO.domain.aiQuiz.presentation.data.request.CodeReviewRequest;
import Coding_GO.codingGO.domain.aiQuiz.presentation.data.response.AiRecommendedResponse;
import Coding_GO.codingGO.domain.aiQuiz.service.AiAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "AI Analysis API", description = "AI 추천 퀴즈 및 코드 리뷰 관련 API")
@RestController
@RequestMapping("/api/CodingGO")
@RequiredArgsConstructor
public class AiController {
    private final AiAnalysisService aiAnalysisService;

    @GetMapping("/ai/recommended-quiz")
    @Operation(summary = "AI 추천 퀴즈 조회", description = "사용자의 실력과 선택한 언어를 기반으로 AI가 맞춤형 추천 퀴즈를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추천 퀴즈 조회 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "인증 실패 (권한 없음)", content = @Content),
            @ApiResponse(responseCode = "500", description = "AI 서비스 연동 오류", content = @Content)
    })
    public ResponseEntity<AiRecommendedResponse> getAiQuiz(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @Parameter(description = "프로그래밍 언어", example = "java") @RequestParam(name = "lang") String lang
    ) {
        return ResponseEntity.ok(aiAnalysisService.getRecommendedQuiz(userId, lang));
    }

    @PostMapping("/save")
    @Operation(summary = "AI 퀴즈 결과 저장", description = "사용자가 진행한 AI 퀴즈의 최종 결과를 서버에 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "결과 저장 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "403", description = "인증 실패")
    })
    public ResponseEntity<Void> saveResult(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @RequestBody AiResultRequest request) {
        aiAnalysisService.saveAiQuizResult(userId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/code-review")
    @Operation(summary = "AI 코드 리뷰 요청", description = "제출한 소스 코드를 AI가 분석하여 개선점과 피드백을 제공합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "코드 리뷰 생성 성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 코드 형식"),
            @ApiResponse(responseCode = "500", description = "AI 분석 중 서버 내부 오류")
    })
    public ResponseEntity<?> reviewCode(
            @RequestBody CodeReviewRequest request
    ) {
        try {
            return ResponseEntity.ok(aiAnalysisService.getCodeReview(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("리뷰 중 오류 발생");
        }
    }
}
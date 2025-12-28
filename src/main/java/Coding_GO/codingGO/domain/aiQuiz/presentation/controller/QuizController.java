package Coding_GO.codingGO.domain.aiQuiz.presentation.controller;

import Coding_GO.codingGO.domain.aiQuiz.presentation.data.request.QuizSubmitRequest;
import Coding_GO.codingGO.domain.aiQuiz.presentation.data.response.QuizFeedback;
import Coding_GO.codingGO.domain.aiQuiz.presentation.data.response.QuizResponse;
import Coding_GO.codingGO.domain.aiQuiz.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "AI Quiz API", description = "AI 기반 퀴즈 생성 및 채점 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping
public class QuizController {

    private final QuizService quizService;


    @GetMapping("/next/{lang}")
    @Operation(summary = "다음 단계 퀴즈 생성", description = "언어와 난이도를 바탕으로 AI가 새로운 퀴즈를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "퀴즈 생성 성공",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 언어 또는 난이도 요청",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "인증 자격 증명이 유효하지 않음",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "AI 서비스 또는 서버 오류 발생",
                    content = @Content
            )
    })
    public ResponseEntity<QuizResponse> getNextStep(
            @AuthenticationPrincipal Long userId,
            @PathVariable String lang,
            @RequestParam String difficulty
    ) {
        QuizResponse response = quizService.getNextStep(userId, lang, difficulty);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/check")
    @Operation(summary = "퀴즈 정답 확인 및 피드백", description = "사용자가 제출한 답안을 AI가 채점하고 상세 피드백을 제공합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "정답 확인 및 피드백 생성 성공",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "유효하지 않은 제출 데이터",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "권한 없음",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류 발생",
                    content = @Content
            )
    })
    public ResponseEntity<QuizFeedback> checkAnswer(
            @AuthenticationPrincipal Long userId,
            @RequestBody QuizSubmitRequest request
    ) {
        return ResponseEntity.ok(quizService.checkAnswer(userId, request));
    }
}

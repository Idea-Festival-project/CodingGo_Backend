package Coding_GO.codingGO.domain.aiQuiz.presentation.data.response;

import lombok.Builder;

import java.util.List;

@Builder
public record QuizResponse(
        Long quizId,
        String question,
        int currentStep,    // 해당 모드 내 현재 단계
        int totalSteps, // 해당 모드 모든 단계 수
        List<String> choices,
        String difficulty
) {
}

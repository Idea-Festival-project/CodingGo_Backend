package Coding_GO.codingGO.domain.aiQuiz.presentation.data.response;

public record AiRecommendedResponse(
        String analysisReport, // ai가 찾은 취약점
        GeneratedQuiz quiz     // 생성된 퀴즈 객체
) {
}


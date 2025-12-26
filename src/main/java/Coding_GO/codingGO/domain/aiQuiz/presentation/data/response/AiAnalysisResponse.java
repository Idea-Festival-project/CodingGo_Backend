package Coding_GO.codingGO.domain.aiQuiz.presentation.data.response;

public record AiAnalysisResponse(
        String analysisReport, // ai가 찾은 취약점
        Long userId
) {
}

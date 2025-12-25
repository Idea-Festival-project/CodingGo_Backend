package Coding_GO.codingGO.domain.aiQuiz.presentation.data.request;

public record AiResultRequest(
        boolean isCorrect,
        String tag,
        String language
) {
}

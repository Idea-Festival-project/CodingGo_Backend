package Coding_GO.codingGO.domain.aiQuiz.presentation.data.response;

public record QuizFeedback(
        boolean isCorrect,
        Integer answerIndex,
        String explanation,
        Integer earnedPoint,
        boolean isCompleted
) {
}


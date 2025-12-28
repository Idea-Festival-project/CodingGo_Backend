package Coding_GO.codingGO.domain.aiQuiz.presentation.data.request;

public record QuizSubmitRequest(
        Long quizId,
        Integer selectedIndex
) {}


package Coding_GO.codingGO.domain.aiQuiz.presentation.data.response;

import java.util.List;

public record GeneratedQuiz(
        String question,
        List<String> choices,
        int answerIndex,
        String tag,
        String explanation
) {
}


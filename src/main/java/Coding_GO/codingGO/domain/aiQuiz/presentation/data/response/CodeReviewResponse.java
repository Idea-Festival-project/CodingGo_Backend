package Coding_GO.codingGO.domain.aiQuiz.presentation.data.response;

import java.util.List;

public record CodeReviewResponse(
        String language,
        boolean isSupported,
        String summary,
        List<String> goodPoints,
        List<String> feedback,
        String securityCheck,
        String improvedCode,
        String advice
) {
}

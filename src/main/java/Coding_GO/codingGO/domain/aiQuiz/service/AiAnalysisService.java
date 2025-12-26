package Coding_GO.codingGO.domain.aiQuiz.service;

import Coding_GO.codingGO.domain.aiQuiz.presentation.data.request.AiResultRequest;
import Coding_GO.codingGO.domain.aiQuiz.presentation.data.request.CodeReviewRequest;
import Coding_GO.codingGO.domain.aiQuiz.presentation.data.response.AiRecommendedResponse;
import Coding_GO.codingGO.domain.aiQuiz.presentation.data.response.CodeReviewResponse;

public interface AiAnalysisService {
    AiRecommendedResponse getRecommendedQuiz(Long userId, String lang);
    void saveAiQuizResult(Long userId, AiResultRequest request);
    CodeReviewResponse getCodeReview(CodeReviewRequest request);
}

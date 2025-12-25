package Coding_GO.codingGO.domain.aiQuiz.service;

import Coding_GO.codingGO.domain.aiQuiz.presentation.data.request.QuizSubmitRequest;
import Coding_GO.codingGO.domain.aiQuiz.presentation.data.response.QuizFeedback;
import Coding_GO.codingGO.domain.aiQuiz.presentation.data.response.QuizResponse;

public interface QuizService {
    QuizResponse getNextStep(Long userId, String lang, String difficulty);

    QuizFeedback checkAnswer(Long userId, QuizSubmitRequest request);
}

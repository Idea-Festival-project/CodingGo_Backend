package Coding_GO.codingGO.domain.aiQuiz.service.impl;

// 1. 엔티티 경로를 domain.aiQuiz.entity로 통일
import Coding_GO.codingGO.domain.aiQuiz.entity.AiQuizEntity;
import Coding_GO.codingGO.domain.aiQuiz.entity.QuizSolveRecordEntity;
import Coding_GO.codingGO.domain.user.entity.UserEntity;
import Coding_GO.codingGO.domain.user.repository.UserRepository;
import Coding_GO.codingGO.domain.aiQuiz.presentation.data.request.QuizSubmitRequest;
import Coding_GO.codingGO.domain.aiQuiz.presentation.data.response.QuizFeedback;
import Coding_GO.codingGO.domain.aiQuiz.presentation.data.response.QuizResponse;
import Coding_GO.codingGO.domain.aiQuiz.repository.AiQuizRepository;
import Coding_GO.codingGO.domain.aiQuiz.repository.QuizSolveRecordRepository;
import Coding_GO.codingGO.domain.aiQuiz.service.QuizService;
import Coding_GO.codingGO.global.exception.ErrorCode;
import Coding_GO.codingGO.global.exception.GlobalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final AiQuizRepository aiQuizRepository;
    private final QuizSolveRecordRepository quizSolveRecordRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    private static final Map<String, Integer> MAX_STAGES = Map.of("EASY", 40, "NORMAL", 30, "HARD", 30);

    @Override
    @Transactional(readOnly = true)
    public QuizResponse getNextStep(Long userId, String lang, String difficulty) {
        String diff = difficulty.toUpperCase();
        long solvedCount = aiQuizRepository.countSolved(userId, lang, diff);
        int totalMax = MAX_STAGES.getOrDefault(diff, 0);

        if (solvedCount >= totalMax) throw new GlobalException(ErrorCode.QUIZ_ALREADY_COMPLETED);

        AiQuizEntity quiz = aiQuizRepository.findFirstUnsolved(userId, lang, diff)
                .orElseThrow(() -> new GlobalException(ErrorCode.QUIZ_ALREADY_COMPLETED));

        try {
            List<String> choices = objectMapper.readValue(quiz.getChoicesJson(), new TypeReference<>() {});
            return new QuizResponse(quiz.getQuizId(), quiz.getQuestionText(), (int)solvedCount + 1, totalMax, choices, diff);
        } catch (JsonProcessingException e) {
            throw new GlobalException(ErrorCode.QUIZ_DATA_ERROR);
        }
    }

    @Override
    @Transactional
    public QuizFeedback checkAnswer(Long userId, QuizSubmitRequest request) {
        AiQuizEntity quiz = aiQuizRepository.findById(request.quizId())
                .orElseThrow(() -> new GlobalException(ErrorCode.QUIZ_NOT_FOUND));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        boolean isCorrect = quiz.getAnswerIndex().equals(request.selectedIndex());
        boolean alreadySolved = quizSolveRecordRepository.existsByUserAndQuiz(user, quiz);

        int points = 0;
        if (!alreadySolved) {
            if (isCorrect) {
                points = switch (quiz.getDifficulty().toUpperCase()) {
                    case "HARD" -> 30;
                    case "NORMAL" -> 20;
                    default -> 10;
                };
                user.addPoint(points); // UserEntity에 addPoint 메서드가 있어야 함
            }

            quizSolveRecordRepository.save(QuizSolveRecordEntity.builder()
                    .user(user).quiz(quiz).isCorrect(isCorrect)
                    .tag(quiz.getTag()).language(quiz.getLanguage()).build());
        }

        long solvedNow = aiQuizRepository.countSolved(userId, quiz.getLanguage(), quiz.getDifficulty());
        boolean isCompleted = solvedNow >= MAX_STAGES.get(quiz.getDifficulty().toUpperCase());

        return new QuizFeedback(isCorrect, quiz.getAnswerIndex(), quiz.getExplanation(), points, isCompleted);
    }
}
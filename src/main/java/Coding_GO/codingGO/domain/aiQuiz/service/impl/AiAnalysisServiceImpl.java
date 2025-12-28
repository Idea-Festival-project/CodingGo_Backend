package Coding_GO.codingGO.domain.aiQuiz.service.impl;

import Coding_GO.codingGO.domain.aiQuiz.entity.QuizSolveRecordEntity;
import Coding_GO.codingGO.domain.aiQuiz.presentation.data.request.AiResultRequest;
import Coding_GO.codingGO.domain.aiQuiz.presentation.data.request.CodeReviewRequest;
import Coding_GO.codingGO.domain.aiQuiz.presentation.data.response.AiRecommendedResponse;
import Coding_GO.codingGO.domain.aiQuiz.presentation.data.response.CodeReviewResponse;
import Coding_GO.codingGO.domain.aiQuiz.presentation.data.response.TagAccuracy;
import Coding_GO.codingGO.domain.aiQuiz.repository.QuizSolveRecordRepository;
import Coding_GO.codingGO.domain.aiQuiz.service.AiAnalysisService;
import Coding_GO.codingGO.domain.user.entity.UserEntity;
import Coding_GO.codingGO.domain.user.repository.UserRepository;
import Coding_GO.codingGO.global.infra.ai.GeminiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiAnalysisServiceImpl implements AiAnalysisService {



    private final QuizSolveRecordRepository quizSolveRecordRepository;
    private final UserRepository userRepository;
    private final GeminiService geminiService;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public AiRecommendedResponse getRecommendedQuiz(Long userId, String lang) {
        List<TagAccuracy> stats = quizSolveRecordRepository.findTagAccuracyByUserId(userId, lang);

        // 성적 데이터가 없으면 '기초'를 기본값으로 설정
        String targetTag = stats.stream()
                .min(Comparator.comparingDouble(TagAccuracy::getAccuracy))
                .map(TagAccuracy::tag).orElse("기초 문법");

        String prompt = String.format(
                "코딩 멘토로서 유저의 %s '%s' 취약점을 분석하고 퀴즈를 내줘. JSON으로만 응답해.\n" +
                        "{\n" +
                        "  \"analysisReport\": \"분석 멘트\",\n" +
                        "  \"quiz\": {\n" +
                        "    \"question\": \"문제\", \"choices\": [\"보기1\",\"보기2\",\"보기3\",\"보기4\"], " +
                        "    \"answerIndex\": 0, \"tag\": \"%s\", \"explanation\": \"해설\"\n" +
                        "  }\n" +
                        "}", lang, targetTag, targetTag);

        String rawResponse = geminiService.askGemini(prompt);
        try {
            String jsonClean = rawResponse.replaceAll("(?s)```json(.*?)```|```(.*?)```", "$1$2").trim();
            return objectMapper.readValue(jsonClean, AiRecommendedResponse.class);
        } catch (Exception e) {
            log.error("JSON 파싱 실패: {}", rawResponse);
            throw new RuntimeException("AI 응답 형식 오류");
        }
    }

    @Transactional
    public void saveAiQuizResult(Long userId, AiResultRequest request) {
        UserEntity user = userRepository.getReferenceById(userId);

        quizSolveRecordRepository.save(QuizSolveRecordEntity.builder()
                .user(user)
                .isCorrect(request.isCorrect())
                .tag(request.tag())
                .language(request.language())
                .build());
    }

    public CodeReviewResponse getCodeReview(CodeReviewRequest request) {
        // AI에게 언어 판별과 리뷰를 동시에 요청
        String prompt = String.format(
                "너는 시니어 보안 개발자야. 다음 코드를 분석해서 JSON으로 응답해.\n\n" +
                        "### 요구사항 ###\n" +
                        "1. 먼저 이 코드가 무슨 언어인지 판별해.\n" +
                        "2. 만약 C, Java, Python 중 하나라면 'isSupported'를 true로 하고 리뷰를 진행해.\n" +
                        "3. 만약 그 외의 언어(HTML, JS, SQL 등)라면 'isSupported'를 false로 하고 리뷰는 생략해.\n" +
                        "4. SQL Injection 등 보안 취약점을 중점적으로 봐줘.\n\n" +
                        "### 유저 코드 ###\n%s\n\n" +
                        "### 응답 형식 ###\n" +
                        "{\n" +
                        "  \"language\": \"판별된 언어 명칭\",\n" +
                        "  \"isSupported\": true/false,\n" +
                        "  \"summary\": \"...\",\n" +
                        "  \"goodPoints\": [], \"feedback\": [], \"securityCheck\": \"...\",\n" +
                        "  \"improvedCode\": \"...\", \"advice\": \"...\"\n" +
                        "}", request.code());

        String response = geminiService.askGemini(prompt);

        try {
            String jsonClean = response.replaceAll("(?s)```json(.*?)```|```(.*?)```", "$1$2").trim();
            CodeReviewResponse review = objectMapper.readValue(jsonClean, CodeReviewResponse.class);

            // 지원하지 않는 언어일 경우 예외 발생 또는 빈 메시지 처리
            if (!review.isSupported()) {
                throw new IllegalArgumentException(review.language() + "은(는) 아직 지원하지 않는 언어입니다.");
            }

            return review;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}


package Coding_GO.codingGO.domain.aiQuiz.repository;

import Coding_GO.codingGO.domain.aiQuiz.entity.AiQuizEntity;
import java.util.Optional; // Optional 임포트 필수

public interface AiQuizRepositoryCustom {
    // 구현체(Impl)와 똑같이 Optional로 선언해야 오류가 안 납니다.
    Optional<AiQuizEntity> findFirstUnsolved(Long userId, String lang, String difficulty);
    long countSolved(Long userId, String lang, String difficulty);
}
package Coding_GO.codingGO.domain.aiQuiz.repository;

import Coding_GO.codingGO.domain.aiQuiz.entity.QuizSolveRecordEntity;
import Coding_GO.codingGO.domain.aiQuiz.entity.AiQuizEntity;
import Coding_GO.codingGO.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizSolveRecordRepository extends JpaRepository<QuizSolveRecordEntity, Integer> , QuizSolveRecordRepositoryCustom {
    boolean existsByUserAndQuiz(UserEntity user, AiQuizEntity quiz);
}

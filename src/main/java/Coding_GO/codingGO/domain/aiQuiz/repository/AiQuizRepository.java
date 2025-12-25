package Coding_GO.codingGO.domain.aiQuiz.repository;

import Coding_GO.codingGO.domain.aiQuiz.entity.AiQuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiQuizRepository extends JpaRepository<AiQuizEntity, Long>, AiQuizRepositoryCustom {
}
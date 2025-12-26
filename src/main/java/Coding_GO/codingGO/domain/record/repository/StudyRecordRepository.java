package Coding_GO.codingGO.domain.record.repository;

import Coding_GO.codingGO.domain.record.entity.StudyRecordEntity;
import Coding_GO.codingGO.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRecordRepository extends JpaRepository<StudyRecordEntity, Long>,
        StudyRecordRepositoryCustom{
    boolean existsByUserAndProblem_ProblemId(UserEntity user, Integer problemId);
    long countByUser_UserId(Long userId);
}

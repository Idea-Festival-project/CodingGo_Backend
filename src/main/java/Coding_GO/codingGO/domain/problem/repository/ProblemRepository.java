package Coding_GO.codingGO.domain.problem.repository;

import Coding_GO.codingGO.domain.problem.entity.ProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<ProblemEntity, Integer>,
        ProblemRepositoryCustom {
}


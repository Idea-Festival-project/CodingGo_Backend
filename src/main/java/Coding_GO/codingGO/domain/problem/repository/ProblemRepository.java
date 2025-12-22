package Coding_GO.codingGO.domain.problem.repository;

import Coding_GO.codingGO.domain.problem.entity.ProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProblemRepository extends JpaRepository<ProblemEntity, Integer>, ProblemRepositoryCustom {
}

package Coding_GO.codingGO.domain.problem.repository;

import Coding_GO.codingGO.domain.problem.presentation.data.response.ProblemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProblemRepositoryCustom {
    Page<ProblemResponse> findAllWithUserStatus(Long userId, int startId, int endId, Pageable pageable);
}

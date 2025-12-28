package Coding_GO.codingGO.domain.problem.service;

import Coding_GO.codingGO.domain.problem.presentation.data.response.ProblemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProblemService {
    Page<ProblemResponse> getProblemList(Long userId, int start, int end, Pageable pageable);
}


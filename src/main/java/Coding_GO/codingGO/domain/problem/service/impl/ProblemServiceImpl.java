package Coding_GO.codingGO.domain.problem.service.impl;

import Coding_GO.codingGO.domain.problem.presentation.data.response.ProblemResponse;
import Coding_GO.codingGO.domain.problem.repository.ProblemRepository;
import Coding_GO.codingGO.domain.problem.service.ProblemService;
import Coding_GO.codingGO.domain.problem.service.RewardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository problemRepository;
    private final RewardService rewardService;

    @Override
    public Page<ProblemResponse> getProblemList(Long userId, int start, int end, Pageable pageable) {

        try {
            rewardService.execute(userId);
        } catch (Exception e) {
            log.error("실시간 동기화 중 오류 발생 (무시하고 리스트만 반환): {}", e.getMessage());
        }
        return problemRepository.findAllWithUserStatus(userId, start, end, pageable);
    }
}

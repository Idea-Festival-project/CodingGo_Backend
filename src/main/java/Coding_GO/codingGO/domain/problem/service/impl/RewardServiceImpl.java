package Coding_GO.codingGO.domain.problem.service.impl;

import Coding_GO.codingGO.domain.problem.data.Tier;
import Coding_GO.codingGO.domain.problem.entity.ProblemEntity;
import Coding_GO.codingGO.domain.problem.repository.ProblemRepository;
import Coding_GO.codingGO.domain.record.entity.StudyRecordEntity;
import Coding_GO.codingGO.domain.record.repository.StudyRecordRepository;
import Coding_GO.codingGO.domain.problem.service.RewardService;
import Coding_GO.codingGO.domain.user.entity.UserEntity;
import Coding_GO.codingGO.domain.user.repository.UserRepository;
import Coding_GO.codingGO.global.exception.ErrorCode;
import Coding_GO.codingGO.global.exception.GlobalException;
import Coding_GO.codingGO.global.infra.SolvedAcClient;
import Coding_GO.codingGO.global.infra.data.TagItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class RewardServiceImpl implements RewardService {

    private final ProblemRepository problemRepository;
    private final SolvedAcClient solvedAcClient;
    private final UserRepository userRepository;
    private final StudyRecordRepository studyRecordRepository;

    @Override
    @Transactional
    @Async
    public void execute(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND_EXCEPTION));

        if (user.getLastSyncAt() != null &&
                user.getLastSyncAt().isAfter(LocalDateTime.now().minusMinutes(1))) {
            return;
        }

        int page = 1;
        boolean hasMoreToSync = true;

        log.info("유저 {}님의 문제 풀이 동기화를 시작합니다.", user.getBojNickname());

        while (true) {
            var response = solvedAcClient.search("s@" + user.getBojNickname(), page);

            if (response == null || response.getItems() == null || response.getItems().isEmpty()) {
                break;
            }

            for (var item : response.getItems()) {

                if (studyRecordRepository.existsByUserAndProblem_ProblemId(user, item.getProblemId())) {

                    log.info("이미 정산된 문제({})를 발견하여 동기화를 조기 종료합니다.", item.getProblemId());
                    hasMoreToSync = false;
                    break;
                }

                ProblemEntity problem = problemRepository.findById(item.getProblemId())
                        .orElseGet(() -> problemRepository.save(
                                ProblemEntity.builder()
                                        .problemId(item.getProblemId())
                                        .title(item.getTitleKo())
                                        .tier(Tier.fromLevel(item.getLevel()).getDescription())
                                        .difficulty(item.getLevel())
                                        .solvedCount(item.getSolvedCount())
                                        .tag(item.getTags().stream()
                                                .map(TagItem::getKey)
                                                .collect(Collectors.joining(",")))
                                        .build()
                        ));

                user.addPoint((problem.getDifficulty() * 10) + 50);

                studyRecordRepository.save(StudyRecordEntity.builder()
                        .user(user)
                        .problem(problem)
                        .isSolved(true)
                        .solvedAt(LocalDateTime.now())
                        .build());
            }

            if (!hasMoreToSync || page * 50 >= response.getCount()) {
                break;
            }

            try {
                Thread.sleep(200); // 0.2초 대기
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            page++;
        }

        user.updateLastSyncAt();
        log.info("유저 {}님의 동기화가 완료되었습니다.", user.getBojNickname());
    }
}
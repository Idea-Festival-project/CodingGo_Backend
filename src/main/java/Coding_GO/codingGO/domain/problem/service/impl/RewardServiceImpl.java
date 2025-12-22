package Coding_GO.codingGO.domain.problem.service.impl;

import Coding_GO.codingGO.domain.problem.entity.ProblemEntity;
import Coding_GO.codingGO.domain.problem.repository.ProblemRepository;
import Coding_GO.codingGO.domain.record.entity.StudyRecordEntity;
import Coding_GO.codingGO.domain.record.repository.StudyRecordRepository;
import Coding_GO.codingGO.domain.problem.service.RewardService;
import Coding_GO.codingGO.global.exception.ErrorCode;
import Coding_GO.codingGO.global.exception.GlobalException;
import Coding_GO.codingGO.global.infra.SolvedAcClient;
import Coding_GO.codingGO.global.infra.data.TagItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RewardServiceImpl implements RewardService {

    private final ProblemRepository problemRepository;
    private final SolvedAcClient solvedAcClient;
    private final UserRepository userRepository;
    private final StudyRecordRepository studyRecordRepository;

    @Override
    @Transactional
    public void execute(Long userId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()-> new GlobalException(ErrorCode.USER_NOT_FOUND_EXCEPTION));

        if (user.getLastSyncAt() != null &&
                user.getLastSyncAt().isAfter(LocalDateTime.now().minusMinutes(1))) {
            log.info("유저 {}님은 아직 쿨타임 중입니다. (최근 동기화: {})",
                    user.getBojHandle(), user.getLastSyncAt());
            return;
        }

        var response = solvedAcClient.search("s@" + user.getBojHandle(), 1);
        if (response == null || response.getItems() == null) return;

        for (var item : response.getItems()) {

            if(!studyRecordRepository.existsByUserAndProblem_ProblemId(user,item.getProblemId())){

                ProblemEntity problem = problemRepository.findById(item.getProblemId())
                        .orElseGet(()->problemRepository.save(
                                ProblemEntity.builder()
                                        .problemId(item.getProblemId())
                                        .title(item.getTitleKo())
                                        .difficulty(item.getLevel())
                                        .tag(String.join("," , item.getTags().stream().map(TagItem::getKey).toList()))
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
        }
    user.updateSyncTime();
    }
}

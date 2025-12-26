package Coding_GO.codingGO.global.scheduler;

import Coding_GO.codingGO.domain.problem.service.RewardService;
import Coding_GO.codingGO.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SyncScheduler {

    private final UserRepository userRepository;
    private final RewardService rewardService;

    @Scheduled(cron = "0 * * * * *")
    public void syncAllUsers() {
        userRepository.findAll().forEach(user -> {
            rewardService.execute(user.getUserId());
        });
    }

}

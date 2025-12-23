package Coding_GO.codingGO.domain.problem.presentation.data.response;

import java.util.List;

public record ProblemResponse(
        Integer problemId,
        String title,
        String tier,
        int solvedCount,
        String tags,
        boolean isSolved,
        Integer rewardPoint
) {




}

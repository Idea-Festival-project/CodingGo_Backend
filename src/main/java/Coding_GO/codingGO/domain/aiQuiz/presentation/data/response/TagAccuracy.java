package Coding_GO.codingGO.domain.aiQuiz.presentation.data.response;

public record TagAccuracy(
        String tag,
        Long totalCount,
        Long correctCount
) {
    public double getAccuracy() {
        return totalCount == 0 ? 0 : (double) correctCount / totalCount * 100;
    }
}


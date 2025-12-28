package Coding_GO.codingGO.domain.record.data;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class InternalStats {
    private long totalSolved;
    private Map<String, Long> tierDistribution; //무슨 난이도 문제를 풀었는지 체크용
}


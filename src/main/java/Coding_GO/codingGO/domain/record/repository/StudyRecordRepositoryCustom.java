package Coding_GO.codingGO.domain.record.repository;

import java.util.Map;

public interface StudyRecordRepositoryCustom {
    Map<String, Long> countTierDistribution(Long userId);
}

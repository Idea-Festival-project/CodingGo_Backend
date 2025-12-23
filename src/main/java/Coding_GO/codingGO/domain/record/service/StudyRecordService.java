package Coding_GO.codingGO.domain.record.service;

import Coding_GO.codingGO.domain.record.data.InternalStats;

public interface StudyRecordService {
   InternalStats getInternalStats(Long userId);
}

package Coding_GO.codingGO.domain.record.service.impl;

import Coding_GO.codingGO.domain.record.data.InternalStats;
import Coding_GO.codingGO.domain.record.repository.StudyRecordRepository;
import Coding_GO.codingGO.domain.record.service.StudyRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyRecordServiceImpl implements StudyRecordService {

    private final StudyRecordRepository studyRecordRepository;

    @Override
    public InternalStats getInternalStats(Long userId) {
        return InternalStats.builder()
                .totalSolved(studyRecordRepository.countByUser_UserId(userId))
                .tierDistribution(studyRecordRepository.countTierDistribution(userId))
                .build();
    }
}


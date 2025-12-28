package Coding_GO.codingGO.domain.record.repository.impl;

import Coding_GO.codingGO.domain.record.repository.StudyRecordRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static Coding_GO.codingGO.domain.record.entity.QStudyRecordEntity.studyRecordEntity;
import static Coding_GO.codingGO.domain.problem.entity.QProblemEntity.problemEntity;

@Repository
@RequiredArgsConstructor
public class StudyRecordRepositoryImpl implements StudyRecordRepositoryCustom {


    private final JPAQueryFactory queryFactory;

    @Override
    public Map<String, Long> countTierDistribution(Long userId) {

        List<Tuple> results = queryFactory
                .select(problemEntity.tier, studyRecordEntity.count())
                .from(studyRecordEntity)
                .join(studyRecordEntity.problem, problemEntity)
                .where(studyRecordEntity.user.userId.eq(userId))
                .groupBy(problemEntity.tier)
                .fetch();


        return results.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(problemEntity.tier),
                        tuple -> tuple.get(studyRecordEntity.count()),
                        (existing, replacement) -> existing
                ));
    }
}
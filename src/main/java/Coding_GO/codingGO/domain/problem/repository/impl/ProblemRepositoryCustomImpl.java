package Coding_GO.codingGO.domain.problem.repository.impl;

import Coding_GO.codingGO.domain.problem.presentation.data.response.ProblemResponse;
import Coding_GO.codingGO.domain.problem.repository.ProblemRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProblemRepositoryCustomImpl implements ProblemRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<ProblemResponse> findAllWithUserStatus(Long userId, int startId, int endId, Pageable pageable) {
        QProblemEntity problem = QProblemEntity.problemEntity;
        QStudyRecordEntity record = QStudyRecordEntity.studyRecordEntity;

        List<ProblemResponse> content = queryFactory
                .select(Projections.constructor(ProblemResponse.class,
                        problem.problemId, problem.title, problem.tier, problem.solvedCount, problem.tags,
                        record.recordId.isNotNull(),
                         problem.difficulty.multiply(10).add(50)
                ))
                .from(problem)
                .leftJoin(record).on(record.problem.eq(problem), record.user.userId.eq(userId))
                .where(problem.problemId.between(startId, endId))
                .orderBy(problem.solvedCount.desc()) // 인기순 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
                .orderBy(problem.solvedCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(problem.count()).from(problem)
                .where(problem.problemId.between(startId, endId)).fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }
}

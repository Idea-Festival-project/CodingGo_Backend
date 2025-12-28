package Coding_GO.codingGO.domain.aiQuiz.repository.impl;

import Coding_GO.codingGO.domain.aiQuiz.entity.QQuizSolveRecordEntity;
import Coding_GO.codingGO.domain.aiQuiz.presentation.data.response.TagAccuracy;
import Coding_GO.codingGO.domain.aiQuiz.repository.QuizSolveRecordRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuizSolveRecordRepositoryCustomImpl implements QuizSolveRecordRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<TagAccuracy> findTagAccuracyByUserId(Long userId, String lang) {
        QQuizSolveRecordEntity record = QQuizSolveRecordEntity.quizSolveRecordEntity;

        return queryFactory
                .select(Projections.constructor(TagAccuracy.class,
                        record.tag,
                        record.count(),

                        new CaseBuilder().when(record.isCorrect.isTrue()).then(1L).otherwise(0L).sum()
                ))
                .from(record)
                .where(record.user.userId.eq(userId), record.language.equalsIgnoreCase(lang))
                .groupBy(record.tag)
                .fetch();
    }
}

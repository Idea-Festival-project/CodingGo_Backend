package Coding_GO.codingGO.domain.aiQuiz.repository.impl;

import Coding_GO.codingGO.domain.aiQuiz.entity.AiQuizEntity;
import Coding_GO.codingGO.domain.aiQuiz.entity.QAiQuizEntity;
import Coding_GO.codingGO.domain.aiQuiz.entity.QQuizSolveRecordEntity;
import Coding_GO.codingGO.domain.aiQuiz.repository.AiQuizRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@RequiredArgsConstructor
public class AiQuizRepositoryCustomImpl implements AiQuizRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<AiQuizEntity> findFirstUnsolved(Long userId, String lang, String difficulty) {
        QAiQuizEntity quiz = QAiQuizEntity.aiQuizEntity;
        QQuizSolveRecordEntity record = QQuizSolveRecordEntity.quizSolveRecordEntity;

        // selectFrom 대신 select().from()으로 나누어 leftJoin 모호성 해결
        return Optional.ofNullable(queryFactory
                .select(quiz)
                .from(quiz)
                .leftJoin(record).on(record.quiz.eq(quiz).and(record.user.userId.eq(userId)))
                .where(
                        quiz.language.eq(lang),
                        quiz.difficulty.equalsIgnoreCase(difficulty),
                        record.solveId.isNull()
                )
                .orderBy(quiz.quizId.asc())
                .fetchFirst());
    }

    @Override
    public long countSolved(Long userId, String lang, String difficulty) {
        QQuizSolveRecordEntity record = QQuizSolveRecordEntity.quizSolveRecordEntity;
        Long count = queryFactory.select(record.count())
                .from(record)
                .where(
                        record.user.userId.eq(userId),
                        record.language.eq(lang),
                        record.quiz.difficulty.equalsIgnoreCase(difficulty)
                )
                .fetchOne();
        return count != null ? count : 0L;
    }
}
package Coding_GO.codingGO.domain.aiQuiz.entity;

import Coding_GO.codingGO.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;


@Entity
@Table(name = "quiz_solve_record",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_solve",
                        columnNames = {"user_id", "quiz_id"}
                )
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
public class QuizSolveRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solve_id")
    private Long solveId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = true)
    private AiQuizEntity quiz;

    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;

    @Column(length = 50)
    private String tag; // 문제 당시의 태그를 복사 저장 (통계용)

    @Column(length = 20)
    private String language; // 언어별 통계를 위해 저장

    @CreationTimestamp
    @Column(name = "solved_at", nullable = false, updatable = false)
    private LocalDateTime solvedAt;
}
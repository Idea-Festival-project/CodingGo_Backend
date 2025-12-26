package Coding_GO.codingGO.domain.record.entity;

import Coding_GO.codingGO.domain.problem.entity.ProblemEntity;
import Coding_GO.codingGO.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "study_record", indexes = {

        @Index(name = "idx_user_problem", columnList = "user_id, problem_id")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class StudyRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private ProblemEntity problem;

    @Column(nullable = false)
    private Boolean isSolved;

    @Column(nullable = false)
    private LocalDateTime solvedAt; // 푼 시간

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column
    private Integer earnedPoint; // 이 문제를 풀었을 때 당시 지급된 포인트
}
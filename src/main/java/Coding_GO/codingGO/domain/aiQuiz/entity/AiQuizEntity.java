package Coding_GO.codingGO.domain.aiQuiz.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;


@Entity
@Table(name = "ai_quiz")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
public class AiQuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long quizId;

    @Column(name = "batch_id", length = 50)
    private String batchId;

    @Column(nullable = false, length = 20)
    private String language;

    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    private String questionText;

    // 데이터베이스의 JSON 타입과 매핑
    @Column(name = "choices_json", nullable = false, columnDefinition = "json")
    private String choicesJson;

    @Column(name = "answer_index", nullable = false)
    private Integer answerIndex;

    @Column(nullable = false, length = 10)
    private String difficulty;

    @Column(length = 50)
    private String tag;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
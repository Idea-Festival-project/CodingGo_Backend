package Coding_GO.codingGO.domain.problem.entity;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "problem")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProblemEntity {
    @Id
    @Column(name = "problem_id")
    private Integer problemId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 255)
    private String tier;

    @Column(name = "difficulty" , nullable = false)
    private Integer difficulty;

    @Column(name = "solved_count", nullable = false)
    private Integer solvedCount;

    @Column(columnDefinition = "TEXT")
    private String tag;
}

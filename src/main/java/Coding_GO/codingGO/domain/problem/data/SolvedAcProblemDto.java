package Coding_GO.codingGO.domain.problem.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SolvedAcProblemDto {
    @JsonProperty("problemId")
    private Integer id;

    @JsonProperty("titleKo")
    private String title;

    private int level; // 난이도 수치
    private int solvedCount; // 푼 사람 수

    private List<TagDto> tags; // 알고리즘 태그 리스트

    @Data
    public static class TagDto {
        private String key; // 태그 이름 (예: greedy, dp)
    }

}

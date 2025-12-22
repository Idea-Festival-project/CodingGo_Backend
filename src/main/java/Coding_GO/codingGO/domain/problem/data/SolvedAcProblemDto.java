package Coding_GO.codingGO.domain.problem.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

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

    // 태그 리스트를 문자열 하나로 합치는 편의 메서드
    public String getTagsAsString() {
        if (tags == null || tags.isEmpty()) return "";
        return tags.stream()
                .map(TagDto::getKey)
                .collect(Collectors.joining(", "));
    }
}

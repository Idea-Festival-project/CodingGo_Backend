package Coding_GO.codingGO.global.infra.data;

import lombok.Getter;

import java.util.List;

@Getter
public class ProblemItem {
    private Integer problemId;
    private String titleKo;
    private int level;
    private int solvedCount;
    private List<TagItem> tags;
}

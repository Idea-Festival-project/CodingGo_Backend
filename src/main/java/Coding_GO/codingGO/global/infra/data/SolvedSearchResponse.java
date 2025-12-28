package Coding_GO.codingGO.global.infra.data;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SolvedSearchResponse {
    private int count;
    private List<ProblemItem> items;
}


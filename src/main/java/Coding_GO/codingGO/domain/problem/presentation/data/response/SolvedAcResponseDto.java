package Coding_GO.codingGO.domain.problem.presentation.data.response;

import Coding_GO.codingGO.domain.problem.data.SolvedAcProblemDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SolvedAcResponseDto {
    private int count;
    private List<SolvedAcProblemDto> items;
}

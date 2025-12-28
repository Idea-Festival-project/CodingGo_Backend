package Coding_GO.codingGO.domain.aiQuiz.repository;

import Coding_GO.codingGO.domain.aiQuiz.presentation.data.response.TagAccuracy;

import java.util.List;

public interface QuizSolveRecordRepositoryCustom {
    List<TagAccuracy> findTagAccuracyByUserId(Long userId, String lang);

}


package Coding_GO.codingGO.domain.community.service.comment;

import Coding_GO.codingGO.domain.community.presentation.data.response.comment.GetCommentResponse;

import java.util.List;

public interface GetCommentService {
    List<GetCommentResponse> execute(Long postId);
}

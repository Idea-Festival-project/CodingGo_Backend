package Coding_GO.codingGO.domain.community.service.comment;

import Coding_GO.codingGO.domain.community.presentation.data.request.comment.CreateCommentRequest;
import Coding_GO.codingGO.domain.community.presentation.data.request.comment.UpdateCommentRequest;
import Coding_GO.codingGO.domain.community.presentation.data.response.comment.CreateCommentResponse;

public interface UpdateCommentService {
    CreateCommentResponse execute(Long commentId, UpdateCommentRequest request, Long userId);
}

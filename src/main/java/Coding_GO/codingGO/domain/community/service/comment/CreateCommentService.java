package Coding_GO.codingGO.domain.community.service.comment;

import Coding_GO.codingGO.domain.community.presentation.data.request.comment.CreateCommentRequest;
import Coding_GO.codingGO.domain.community.presentation.data.response.comment.CreateCommentResponse;

public interface CreateCommentService {
    CreateCommentResponse execute(Long postId,Long user_id,CreateCommentRequest request);
}

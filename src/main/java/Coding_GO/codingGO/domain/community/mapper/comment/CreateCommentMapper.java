package Coding_GO.codingGO.domain.community.mapper.comment;

import Coding_GO.codingGO.domain.community.data.Comment;
import Coding_GO.codingGO.domain.community.entity.CommentEntity;
import Coding_GO.codingGO.domain.community.presentation.data.response.comment.CreateCommentResponse;
import org.springframework.stereotype.Component;

@Component
public class CreateCommentMapper {
    public CreateCommentResponse toResponse(CommentEntity comment) {

        return new CreateCommentResponse(
                comment.getCommentId(),
                comment.getContent(),
                comment.getCreate_at()
        );
    }
}

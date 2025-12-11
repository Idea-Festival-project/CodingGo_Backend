package Coding_GO.codingGO.domain.community.mapper.comment;

import Coding_GO.codingGO.domain.community.entity.CommentEntity;
import Coding_GO.codingGO.domain.community.presentation.data.response.comment.GetCommentResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GetCommentMapper {

    public GetCommentResponse getCommentResponse(CommentEntity entity) {

        return GetCommentResponse.builder()
                .commentId(entity.getCommentId())
                .userId(entity.getAuthor().getUserId())
                .username(entity.getAuthor().getUserName())
                .profileImageUrl(entity.getAuthor().getProfileImageUrl())
                .content(entity.getContent())
                .parentCommentId(entity.getParentComment() != null ? entity.getParentComment().getCommentId() : null)
                .createdAt(entity.getCreate_at())
                .babyComment(new ArrayList<>())
                .build();
    }

    public List<GetCommentResponse> buildCommentTree(List<CommentEntity> comments) {
        Map<Long, GetCommentResponse> map = new HashMap<>();
        List<GetCommentResponse> babyComments = new ArrayList<>();
        for (CommentEntity comment : comments) {

            GetCommentResponse response = getCommentResponse(comment);

            map.put(comment.getCommentId(), response);

            Long parentCommentId = response.getParentCommentId();

            if (parentCommentId == null) {
                babyComments.add(response);
            } else {
                GetCommentResponse parent = map.get(parentCommentId);
                parent.getBabyComment().add(response);
            }
        }
        return babyComments;
    }
}
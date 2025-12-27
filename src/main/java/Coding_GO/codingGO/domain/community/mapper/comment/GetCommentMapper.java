package Coding_GO.codingGO.domain.community.mapper.comment;

import Coding_GO.codingGO.domain.community.entity.CommentEntity;
import Coding_GO.codingGO.domain.community.presentation.data.response.comment.GetCommentResponse;
import Coding_GO.codingGO.domain.profile.entity.UserProfileEntity;
import Coding_GO.codingGO.domain.user.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GetCommentMapper {

    public GetCommentResponse getCommentResponse(CommentEntity entity) {

        UserEntity author = entity.getAuthor();
        UserProfileEntity profile = author.getProfile();

        String base64Image = (profile != null && profile.getProfileImage() != null)
                ? Base64.getEncoder().encodeToString(profile.getProfileImage())
                : null;

        return GetCommentResponse.builder()
                .commentId(entity.getCommentId())
                .userId(author.getUserId())
                .username(profile != null ? profile.getNickname() : "알 수 없는 사용자")
                .profileImageUrl(base64Image)
                .content(entity.getContent())
                .parentCommentId(entity.getParentComment() != null ? entity.getParentComment().getCommentId() : null)
                .createdAt(entity.getCreate_at())
                .babyComment(new ArrayList<>())
                .build();
    }

    public List<GetCommentResponse> buildCommentTree(List<CommentEntity> comments) {
        Map<Long, GetCommentResponse> commentMap = new HashMap<>();
        List<GetCommentResponse> babyComments = new ArrayList<>();

        for (CommentEntity comment : comments) {
            GetCommentResponse response = getCommentResponse(comment);
            commentMap.put(comment.getCommentId(), response);
        }

        for (GetCommentResponse response : commentMap.values()) {
            Long parentCommentId = response.getParentCommentId();

            if (parentCommentId == null) {
                babyComments.add(response);
            } else {
                GetCommentResponse parent = commentMap.get(parentCommentId);
                if (parent != null) {
                    parent.getBabyComment().add(response);
                }
            }
        }
        return babyComments;
    }
}
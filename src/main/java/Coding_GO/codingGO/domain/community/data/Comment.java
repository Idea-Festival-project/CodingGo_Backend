package Coding_GO.codingGO.domain.community.data;

import Coding_GO.codingGO.domain.community.data.constant.CommunityCategory;
import Coding_GO.codingGO.domain.community.entity.CommentEntity;
import Coding_GO.codingGO.domain.community.entity.CommunityEntity;
import Coding_GO.codingGO.domain.user.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    Long commentId;
    CommunityEntity post;
    UserEntity author;
    CommunityCategory category;
    CommentEntity parentComment;
    String content;
    LocalDateTime create_at;

    @Builder.Default
    private List<CommentEntity> babyComments = new ArrayList<>();
}

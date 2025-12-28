package Coding_GO.codingGO.domain.community.data;

import Coding_GO.codingGO.domain.community.data.constant.CommunityCategory;
import Coding_GO.codingGO.domain.community.entity.CommunityEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Community {
    private Long postId;
    private Long userId;
    private String username;
    private String nickname;
    private String profileImage;
    private CommunityCategory category;
    private String title;
    private String content;
    private Integer commentCount;
    private LocalDateTime createdAt;


    public Community(Long postId,Long userId,
                     String username, String nickname,
                     String profileImage, CommunityCategory category,
                     String title, String content,
                     Integer commentCount) {
        this.postId = postId;
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.category = category;
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.createdAt = LocalDateTime.now();
    }
}

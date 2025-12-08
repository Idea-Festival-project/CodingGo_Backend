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
}

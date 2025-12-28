package Coding_GO.codingGO.domain.community.presentation.data.response.community;

import Coding_GO.codingGO.domain.community.data.Community;
import Coding_GO.codingGO.domain.community.data.constant.CommunityCategory;

import java.time.LocalDateTime;

public record CreateCommunityResponse(
        Long postId,
        String nickname,
        String profileImage,
        CommunityCategory category,
        String content,
        Integer commentCount,
        LocalDateTime createdAt
) {

    public static CreateCommunityResponse from(Community dto) {
        return new CreateCommunityResponse(
                dto.getPostId(),
                dto.getUsername(),
                dto.getProfileImage(),
                dto.getCategory(),
                dto.getContent(),
                dto.getCommentCount(),
                dto.getCreatedAt()
        );
    }
}

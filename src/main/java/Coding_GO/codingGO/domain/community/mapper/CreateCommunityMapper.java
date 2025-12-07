package Coding_GO.codingGO.domain.community.mapper;

import Coding_GO.codingGO.domain.community.data.Community;
import Coding_GO.codingGO.domain.community.data.constant.CommunityCategory;
import Coding_GO.codingGO.domain.community.entity.CommunityEntity;
import org.springframework.stereotype.Component;

@Component
public class CreateCommunityMapper {

    public Community toDto(CommunityEntity entity) {
        String nickname = entity.getAuthor().getProfile() != null
                ? entity.getAuthor().getProfile().getNickname()
                : entity.getAuthor().getUsername();

        String profileImage = entity.getAuthor().getProfile() != null
                ?entity.getAuthor().getProfile().getProfileImage()
                : null;

        return Community.builder()
                .postId(entity.getPostId())
                .userId(entity.getAuthor().getUserId())
                .username(entity.getAuthor().getUsername())
                .nickname(nickname)
                .profileImage(profileImage)
                .category(entity.getCategory())
                .title(entity.getTitle())
                .content(entity.getContent())
                .commentCount(0) //TODO: 실제 댓글 수 계산
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public CommunityEntity createEntity(CommunityCategory category, String content, UserEntity author){
        return CommunityEntity.builder()
                .author(author)
                .category(category)
                .title(author.getUsername())
                .content(content)
                .build();
    }

}

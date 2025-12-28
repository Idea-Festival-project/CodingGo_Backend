package Coding_GO.codingGO.domain.community.mapper.communirty;

import Coding_GO.codingGO.domain.community.data.Community;
import Coding_GO.codingGO.domain.community.data.constant.CommunityCategory;
import Coding_GO.codingGO.domain.community.entity.CommunityEntity;
import Coding_GO.codingGO.domain.user.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class CreateCommunityMapper {


    public Community toDto(CommunityEntity entity) {
        UserEntity author = entity.getAuthor();

        String nickname = author.getProfile() != null
                ? author.getProfile().getNickname()
                : author.getNickname();

        String profileImage = (author.getProfile() != null && author.getProfile().getProfileImage() != null)
                ? Base64.getEncoder().encodeToString(author.getProfile().getProfileImage())
                : null;

        return Community.builder()
                .postId(entity.getPostId())
                .userId(author.getUserId())
                .username(author.getNickname())
                .nickname(nickname)
                .profileImage(profileImage)
                .category(entity.getCategory())
                .title(entity.getTitle())
                .content(entity.getContent())
                .commentCount(0)
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public CommunityEntity createEntity(CommunityCategory category, String content, UserEntity author) {
        return CommunityEntity.builder()
                .author(author)
                .category(category)
                .title(author.getNickname())
                .content(content)
                .build();
    }
}
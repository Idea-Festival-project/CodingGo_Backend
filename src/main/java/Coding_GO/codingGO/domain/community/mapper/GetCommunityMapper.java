package Coding_GO.codingGO.domain.community.mapper;

import Coding_GO.codingGO.domain.community.data.Community;
import Coding_GO.codingGO.domain.community.entity.CommunityEntity;
import Coding_GO.codingGO.domain.community.presentation.data.AuthorInfo;
import Coding_GO.codingGO.domain.community.presentation.data.CommunityData;
import Coding_GO.codingGO.domain.community.presentation.data.CommunityInfo;
import Coding_GO.codingGO.domain.community.presentation.data.CommunityPageInfo;
import Coding_GO.codingGO.domain.community.presentation.data.response.GetCommunityListResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetCommunityMapper {
    public Community toDto(CommunityEntity entity) {
        String nickname = entity.getAuthor().getProfile() != null
                ? entity.getAuthor().getProfile().getNickname()
                : entity.getAuthor().getUsername();

        String profileImage = entity.getAuthor().getProfile() != null
                ? entity.getAuthor().getProfile().getProfileImage()
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

    public GetCommunityListResponse toCommunityListResponse(Page<Community> page) {

        List<CommunityInfo> posts = page.getContent().stream()
                .map(c -> new CommunityInfo(
                        c.getPostId(),
                        c.getCategory().
                        c.getContent(),
                        new AuthorInfo(c.getUserId(), c.getUsername(), c.getProfileImage()),
                        c.getCommentCount(),
                        c.getCreatedAt()
                ))
                .toList();

        CommunityPageInfo pageInfo = new CommunityPageInfo(
                page.getNumber() + 1,
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext()
        );
        return new GetCommunityListResponse(
                "ok",
                new CommunityData(pageInfo, posts)
        );
    }
}


package Coding_GO.codingGO.domain.community.mapper.communirty;

import Coding_GO.codingGO.domain.community.data.Community;
import Coding_GO.codingGO.domain.community.entity.CommentEntity;
import Coding_GO.codingGO.domain.community.presentation.data.AuthorInfo;
import Coding_GO.codingGO.domain.community.presentation.data.CommunityData;
import Coding_GO.codingGO.domain.community.presentation.data.CommunityInfo;
import Coding_GO.codingGO.domain.community.presentation.data.CommunityPageInfo;
import Coding_GO.codingGO.domain.community.presentation.data.response.comment.GetCommentResponse;
import Coding_GO.codingGO.domain.community.presentation.data.response.community.GetCommunityListResponse;
import Coding_GO.codingGO.domain.profile.entity.UserProfileEntity;
import Coding_GO.codingGO.domain.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class GetCommunityMapper {
    public GetCommentResponse toDto(CommentEntity entity) {
        UserEntity author = entity.getAuthor();
        UserProfileEntity profile = author.getProfile();

        // 1. 이미지 byte[]를 Base64 문자열로 변환
        String base64Image = (profile != null && profile.getProfileImage() != null)
                ? Base64.getEncoder().encodeToString(profile.getProfileImage())
                : null;

        return GetCommentResponse.builder()
                .commentId(entity.getCommentId())
                .userId(author.getUserId())
                // 2. username은 profile의 nickname을 사용하거나 author의 필드 사용
                .username(profile != null ? profile.getNickname() : author.getNickname())
                .profileImageUrl(base64Image) // 이제 String 타입으로 정상 할당됩니다.
                .content(entity.getContent())
                .parentCommentId(entity.getParentComment() != null ? entity.getParentComment().getCommentId() : null)
                .createdAt(entity.getCreate_at())
                .babyComment(new ArrayList<>())
                .build();
    }

    public GetCommunityListResponse toCommunityListResponse(Page<Community> page) {

        List<CommunityInfo> posts = page.getContent().stream()
                .map(c -> new CommunityInfo(
                        c.getPostId(),
                        c.getCategory(),
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


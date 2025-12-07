package Coding_GO.codingGO.domain.community.presentation.data;

import Coding_GO.codingGO.domain.community.data.constant.CommunityCategory;

import java.time.LocalDateTime;

public record CommunityInfo(
        Long post_id,
        CommunityCategory category,
        String content,
        AuthorInfo author,
        Integer commentCount,
        LocalDateTime createAt
) {}

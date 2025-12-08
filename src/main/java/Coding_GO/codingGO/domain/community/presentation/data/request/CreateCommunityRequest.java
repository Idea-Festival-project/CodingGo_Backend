package Coding_GO.codingGO.domain.community.presentation.data.request;

import Coding_GO.codingGO.domain.community.data.constant.CommunityCategory;

public record CreateCommunityRequest(
        CommunityCategory category,
        String content
) {
}

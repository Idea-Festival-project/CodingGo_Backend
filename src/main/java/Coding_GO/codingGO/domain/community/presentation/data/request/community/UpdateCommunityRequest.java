package Coding_GO.codingGO.domain.community.presentation.data.request.community;

import Coding_GO.codingGO.domain.community.data.constant.CommunityCategory;

public record UpdateCommunityRequest(
        String content,
        CommunityCategory category
) {
}

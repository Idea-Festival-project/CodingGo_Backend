package Coding_GO.codingGO.domain.community.presentation.data;

import java.util.List;

public record CommunityData(
        CommunityPageInfo communityInfo,
        List<CommunityInfo> community
) {
}

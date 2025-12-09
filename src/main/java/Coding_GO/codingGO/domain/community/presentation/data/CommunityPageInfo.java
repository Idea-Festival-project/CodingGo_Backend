package Coding_GO.codingGO.domain.community.presentation.data;

public record CommunityPageInfo(
        int page,
        int limit,
        long totalElements,
        int totalPage,
        boolean hasNext
) {
}

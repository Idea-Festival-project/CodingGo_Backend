package Coding_GO.codingGO.domain.friend.presentation.data.response;

import Coding_GO.codingGO.domain.friend.data.GetPendingFriendRequestResponseItemDto;
import lombok.Builder;

import java.util.List;

@Builder
public record GetPendingFriendRequestResponse(
        GetFriendRequestResponse data
) {
    @Builder
    public record GetFriendRequestResponse(
        List<GetPendingFriendRequestResponseItemDto> items,

        Integer totalCount,

        Integer currentPage,

        Integer totalPage
    ) {
    }
}



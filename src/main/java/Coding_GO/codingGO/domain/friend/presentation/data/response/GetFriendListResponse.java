package Coding_GO.codingGO.domain.friend.presentation.data.response;

import Coding_GO.codingGO.domain.friend.presentation.data.request.FriendItem;
import lombok.Builder;

import java.util.List;

@Builder
public record GetFriendListResponse(
        Friend data
) {
    @Builder
    public record Friend(
            List<FriendItem> friend,

            Integer totalCount,

            Integer currentPage,

            Integer totalPage
    ){}

}

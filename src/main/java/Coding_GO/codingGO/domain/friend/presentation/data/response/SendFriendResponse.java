package Coding_GO.codingGO.domain.friend.presentation.data.response;

import Coding_GO.codingGO.domain.friend.data.constant.FriendshipStatus;
import lombok.Builder;
import lombok.Data;

@Builder
public record SendFriendResponse(
        Long friendId,
        FriendshipStatus status
){}


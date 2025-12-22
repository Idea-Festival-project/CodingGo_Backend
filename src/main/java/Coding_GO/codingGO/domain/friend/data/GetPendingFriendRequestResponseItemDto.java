package Coding_GO.codingGO.domain.friend.data;

import Coding_GO.codingGO.domain.friend.data.constant.FriendshipStatus;
import lombok.Builder;

@Builder
public record GetPendingFriendRequestResponseItemDto(
        Long friendId,

        Long userId,

        String username,

        String nickname,

        String profileImageUrl,

        FriendshipStatus status
) {}

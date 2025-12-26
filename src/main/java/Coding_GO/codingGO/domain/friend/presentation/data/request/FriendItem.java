package Coding_GO.codingGO.domain.friend.presentation.data.request;

import lombok.Builder;

@Builder
public record FriendItem(
        Long userId,
        String username,
        String nickname,
        String profileImageUrl,
        Long friendId
) {}

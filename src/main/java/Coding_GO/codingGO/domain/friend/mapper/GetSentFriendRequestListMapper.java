package Coding_GO.codingGO.domain.friend.mapper;

import Coding_GO.codingGO.domain.friend.data.GetPendingFriendRequestResponseItemDto;
import Coding_GO.codingGO.domain.friend.entity.FriendEntity;
import Coding_GO.codingGO.domain.user.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component

public class GetSentFriendRequestListMapper {

    public GetPendingFriendRequestResponseItemDto toGetSentFriendRequestResponseItemDto(FriendEntity friendEntity) {
        UserEntity friendUser = friendEntity.getFriend();

        String profileImageUrl = (friendUser.getProfile() != null && friendUser.getProfile().getProfileImage() != null)
                ? Base64.getEncoder().encodeToString(friendUser.getProfile().getProfileImage())
                : null;

        String nickname = (friendUser.getProfile() != null && friendUser.getProfile().getNickname() != null)
                ? friendUser.getProfile().getNickname()
                : friendUser.getNickname();

        return GetPendingFriendRequestResponseItemDto.builder()
                .friendId(friendEntity.getFriendshipId())
                .userId(friendUser.getUserId())
                .username(friendUser.getNickname())
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .status(friendEntity.getStatus())
                .build();
    }

    public List<GetPendingFriendRequestResponseItemDto> toGetSentFriendRequestResponseItemDtoList(List<FriendEntity> friendEntities) {
        return friendEntities.stream()
                .map(this::toGetSentFriendRequestResponseItemDto)
                .collect(Collectors.toList());
    }
}
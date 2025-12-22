package Coding_GO.codingGO.domain.friend.mapper;

import Coding_GO.codingGO.domain.friend.data.GetPendingFriendRequestResponseItemDto;
import Coding_GO.codingGO.domain.friend.data.constant.FriendshipStatus;
import Coding_GO.codingGO.domain.friend.entity.FriendEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetSentFriendRequestListMapper {
    public GetPendingFriendRequestResponseItemDto toGetSentFriendRequestResponseItemDto(FriendEntity friendEntity) {

        UserEntity friendUser = friendEntity.getAuthor();

        return GetPendingFriendRequestResponseItemDto.builder()
                .friendId(friendEntity.getFriendshipId())
                .username(friendUser.getUserId())
                .username(friendUser.getUserName())
                .nickname(friendUser.getNickname())
                .profileImageUrl(friendUser.getProfileImageUrl())
                .status(friendEntity.getStatus().equals(FriendshipStatus.PENDING))
                .build();
    }
    public List<GetPendingFriendRequestResponseItemDto> toGetSentFriendRequestResponseItemDtoList(List<FriendEntity> friendEntities) {
        return friendEntities.stream()
                .map(this::toGetPendingFriendRequestResponseItemDto)
                .collect(Collectors.toList());
    }
}

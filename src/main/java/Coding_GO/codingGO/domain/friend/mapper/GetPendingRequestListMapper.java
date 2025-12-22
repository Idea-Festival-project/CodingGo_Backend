package Coding_GO.codingGO.domain.friend.mapper;

import Coding_GO.codingGO.domain.friend.data.GetPendingFriendRequestResponseItemDto;
import Coding_GO.codingGO.domain.friend.data.constant.FriendshipStatus;
import Coding_GO.codingGO.domain.friend.entity.FriendEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetPendingRequestListMapper {
    public GetPendingFriendRequestResponseItemDto toGetPendingFriendRequestResponseItemDto(FriendEntity friendEntity) {

        UserEntity author = friendEntity.getAuthor();

        return GetPendingFriendRequestResponseItemDto.builder()
                .friendId(friendEntity.getFriendshipId())
                .username(author.getUserId())
                .username(author.getUserName())
                .nickname(author.getNickname())
                .profileImageUrl(author.getProfileImageUrl())
                .status(friendEntity.getStatus().equals(FriendshipStatus.PENDING))
        .build();
    }
    public List<GetPendingFriendRequestResponseItemDto> toGetPendingFriendRequestResponseItemDtoList(List<FriendEntity> friendEntities) {
        return friendEntities.stream()
                .map(this::toGetPendingFriendRequestResponseItemDto)
                .collect(Collectors.toList());
    }
}

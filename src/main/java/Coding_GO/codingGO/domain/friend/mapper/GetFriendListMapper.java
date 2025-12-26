package Coding_GO.codingGO.domain.friend.mapper;

import Coding_GO.codingGO.domain.friend.entity.FriendEntity;
import Coding_GO.codingGO.domain.friend.presentation.data.request.FriendItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetFriendListMapper {
    public FriendItem toFriendItemDto(FriendEntity friendEntity, Long userId) {

        UserEntity friendUser = friendEntity.getAuthor().getUserId().equals(userId) ? friendEntity.getFriend() : friendEntity.getAuthor();

        return FriendItem.builder()
                .userId(friendUser.getUserId())
                .username(friendUser.getUsername())
                .nickname(friendUser.getNickname())
                .profileImageUrl(friendUser.getProfileImageUrl())
                .friendId(friendEntity.getFriendshipId())
                .build();
    }

    public List<FriendItem> toFriendItemDtoList(List<FriendEntity> friendEntities, Long userId) {
        return friendEntities.stream()
                .map(entity -> toFriendItemDto(entity,userId))
                .collect(Collectors.toList());
    }
}

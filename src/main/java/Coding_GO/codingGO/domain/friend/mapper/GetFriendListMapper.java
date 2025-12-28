package Coding_GO.codingGO.domain.friend.mapper;

import Coding_GO.codingGO.domain.friend.entity.FriendEntity;
import Coding_GO.codingGO.domain.friend.presentation.data.request.FriendItem;
import Coding_GO.codingGO.domain.user.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetFriendListMapper {

    public FriendItem toFriendItemDto(FriendEntity friendEntity, Long userId) {

        UserEntity friendUser = friendEntity.getAuthor().getUserId().equals(userId)
                ? friendEntity.getFriend()
                : friendEntity.getAuthor();

        String profileImageUrl = (friendUser.getProfile() != null && friendUser.getProfile().getProfileImage() != null)
                ? Base64.getEncoder().encodeToString(friendUser.getProfile().getProfileImage())
                : null;

        String nickname = (friendUser.getProfile() != null && friendUser.getProfile().getNickname() != null)
                ? friendUser.getProfile().getNickname()
                : friendUser.getNickname();

        return FriendItem.builder()
                .userId(friendUser.getUserId())
                .username(friendUser.getNickname())
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .friendId(friendEntity.getFriendshipId())
                .build();
    }

    public List<FriendItem> toFriendItemDtoList(List<FriendEntity> friendEntities, Long userId) {
        return friendEntities.stream()
                .map(entity -> toFriendItemDto(entity, userId))
                .collect(Collectors.toList());
    }

}
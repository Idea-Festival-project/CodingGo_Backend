package Coding_GO.codingGO.domain.friend.mapper;

import Coding_GO.codingGO.domain.friend.data.GetPendingFriendRequestResponseItemDto;
import Coding_GO.codingGO.domain.friend.entity.FriendEntity;
import Coding_GO.codingGO.domain.user.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class GetPendingRequestListMapper {

    public GetPendingFriendRequestResponseItemDto toGetPendingFriendRequestResponseItemDto(FriendEntity friendEntity) {
        UserEntity author = friendEntity.getAuthor();

        String profileImageUrl = (author.getProfile() != null && author.getProfile().getProfileImage() != null)
                ? Base64.getEncoder().encodeToString(author.getProfile().getProfileImage())
                : null;

        String nickname = (author.getProfile() != null && author.getProfile().getNickname() != null)
                ? author.getProfile().getNickname()
                : author.getNickname();

        return GetPendingFriendRequestResponseItemDto.builder()
                .friendId(friendEntity.getFriendshipId())
                .userId(author.getUserId())
                .username(author.getNickname())
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .status(friendEntity.getStatus())
                .build();
    }

    public List<GetPendingFriendRequestResponseItemDto> toGetPendingFriendRequestResponseItemDtoList(List<FriendEntity> friendEntities) {
        return friendEntities.stream()
                .map(this::toGetPendingFriendRequestResponseItemDto)
                .collect(Collectors.toList());
    }
}
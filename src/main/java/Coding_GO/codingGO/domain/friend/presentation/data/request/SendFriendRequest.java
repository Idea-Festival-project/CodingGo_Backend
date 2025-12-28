package Coding_GO.codingGO.domain.friend.presentation.data.request;


import jakarta.validation.constraints.NotNull;

public record SendFriendRequest(
        @NotNull(message = "friend_id는 필수입니다.")
        Long friendId
) {

}

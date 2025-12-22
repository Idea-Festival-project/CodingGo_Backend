package Coding_GO.codingGO.domain.friend.service;

import Coding_GO.codingGO.domain.friend.presentation.data.response.GetFriendListResponse;

public interface DeleteFriendService {
    void deleteFriend(Long targetUserId,Long userId);
}

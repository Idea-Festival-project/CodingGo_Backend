package Coding_GO.codingGO.domain.friend.service;

import Coding_GO.codingGO.domain.friend.presentation.data.response.GetPendingFriendRequestResponse;

public interface GetSentFriendRequestService {
    GetPendingFriendRequestResponse execute(Long userId, int page, int limit);
}
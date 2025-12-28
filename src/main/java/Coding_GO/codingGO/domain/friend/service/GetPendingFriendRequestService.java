package Coding_GO.codingGO.domain.friend.service;

import Coding_GO.codingGO.domain.friend.presentation.data.response.GetPendingFriendRequestResponse;

public interface GetPendingFriendRequestService {

    GetPendingFriendRequestResponse execute(Long userId, int page,int limit);

}


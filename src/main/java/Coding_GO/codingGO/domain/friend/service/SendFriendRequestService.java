package Coding_GO.codingGO.domain.friend.service;

import Coding_GO.codingGO.domain.friend.presentation.data.request.SendFriendRequest;
import Coding_GO.codingGO.domain.friend.presentation.data.response.SendFriendResponse;

public interface SendFriendRequestService {
    SendFriendResponse execute(Long userId, SendFriendRequest request);
}


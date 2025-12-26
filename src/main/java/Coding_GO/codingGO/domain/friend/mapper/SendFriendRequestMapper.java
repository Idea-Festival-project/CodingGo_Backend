package Coding_GO.codingGO.domain.friend.mapper;

import Coding_GO.codingGO.domain.friend.entity.FriendEntity;
import Coding_GO.codingGO.domain.friend.presentation.data.request.SendFriendRequest;
import Coding_GO.codingGO.domain.friend.presentation.data.response.SendFriendResponse;
import org.springframework.stereotype.Component;

@Component
public class SendFriendRequestMapper {
    public SendFriendResponse toResponse(FriendEntity friend){
        return SendFriendResponse.builder()
                .friendId(friend.getFriend().getUserId())
                .friendId(friend.getStatus())
                .build();
    }
}



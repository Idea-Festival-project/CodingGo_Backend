package Coding_GO.codingGO.domain.friend.service.impl;

import Coding_GO.codingGO.domain.friend.data.constant.FriendshipStatus;
import Coding_GO.codingGO.domain.friend.entity.FriendEntity;
import Coding_GO.codingGO.domain.friend.repository.FriendRepository;
import Coding_GO.codingGO.domain.friend.service.DeleteFriendService;
import Coding_GO.codingGO.global.exception.ErrorCode;
import Coding_GO.codingGO.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteFriendServiceImpl implements DeleteFriendService {

    private final FriendRepository friendRepository;

    @Override
    public void execute(Long targetUserId, Long userId) {

        if(userId.equals(targetUserId)){
            throw new GlobalException(ErrorCode.SELF_FRIEND_DELETE);
        }

        FriendEntity friend = friendRepository.findByFriendShip(userId,targetUserId)
                .or(()-> friendRepository.findByFriendShip(targetUserId,userId))
                .orElseThrow(()->new GlobalException(ErrorCode.FRIEND_NOT_FOUND));

        if (friend.getStatus() != FriendshipStatus.ACCEPTED) {
            throw new GlobalException(ErrorCode.NOT_FRIEND);
        }

        friendRepository.delete(friend);


    }
}

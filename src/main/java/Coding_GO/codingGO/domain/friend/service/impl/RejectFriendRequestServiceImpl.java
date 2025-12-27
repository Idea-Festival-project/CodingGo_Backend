package Coding_GO.codingGO.domain.friend.service.impl;

import Coding_GO.codingGO.domain.friend.data.constant.FriendshipStatus;
import Coding_GO.codingGO.domain.friend.entity.FriendEntity;
import Coding_GO.codingGO.domain.friend.presentation.data.response.GetFriendListResponse;
import Coding_GO.codingGO.domain.friend.repository.FriendRepository;
import Coding_GO.codingGO.domain.friend.service.RejectFriendRequestService;
import Coding_GO.codingGO.global.exception.ErrorCode;
import Coding_GO.codingGO.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RejectFriendRequestServiceImpl implements RejectFriendRequestService {

    private final FriendRepository friendRepository;

    @Override
    public void execute(Long userId, Long friendId) {

        FriendEntity friend = friendRepository.findById(friendId)
                .orElseThrow(()-> new GlobalException(ErrorCode.FRIEND_NOT_FOUND));

        if (!friend.getFriend().getUserId().equals(userId)){
            throw new GlobalException(ErrorCode.UNAUTHORIZED_EXCEPTION);
        }
        if (friend.getStatus() != FriendshipStatus.PENDING){
            throw new GlobalException(ErrorCode.INVALID_STATUS);
        }

        friend.setStatus(FriendshipStatus.REJECTED);
        friendRepository.save(friend);
    }
    }


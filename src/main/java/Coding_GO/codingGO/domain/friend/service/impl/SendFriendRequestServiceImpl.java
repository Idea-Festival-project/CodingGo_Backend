package Coding_GO.codingGO.domain.friend.service.impl;

import Coding_GO.codingGO.domain.friend.data.constant.FriendshipStatus;
import Coding_GO.codingGO.domain.friend.entity.FriendEntity;
import Coding_GO.codingGO.domain.friend.mapper.SendFriendRequestMapper;
import Coding_GO.codingGO.domain.friend.presentation.data.request.SendFriendRequest;
import Coding_GO.codingGO.domain.friend.presentation.data.response.SendFriendResponse;
import Coding_GO.codingGO.domain.friend.repository.FriendRepository;
import Coding_GO.codingGO.domain.friend.repository.SendFriendRequestRepositoryCustom;
import Coding_GO.codingGO.domain.friend.repository.impl.SendFriendRequestRepositoryImpl;
import Coding_GO.codingGO.domain.friend.service.SendFriendRequestService;
import Coding_GO.codingGO.global.exception.ErrorCode;
import Coding_GO.codingGO.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SendFriendRequestServiceImpl implements SendFriendRequestService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final SendFriendRequestMapper mapper;
    @Override
    public SendFriendResponse execute(Long userId, SendFriendRequest request) {
        Long friendId = request.friendId();

        if(userId.equals(friendId)){
            throw new GlobalException(ErrorCode.COMMUNITY_NOT_FOUND_EXCEPTION);
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND_EXCEPTION));
        UserEntity friend = friendRepository.findById(friendId)
                .orElseThrow(() -> new GlobalException(ErrorCode.FRIEND_NOT_FOUND));

        var checkFriendShip = friendRepository.findByFriendShip(userId,friendId)
                .or(()-> friendRepository.findByFriendShip(friendId,userId));

        if(checkFriendShip.isPresent()){
            FriendEntity friendEntity = checkFriendShip.get();
            if(friendEntity.getStatus() == FriendshipStatus.ACCEPTED){
                throw new GlobalException(ErrorCode.ALREADY_FRIENDS);
            }
            if(friendEntity.getStatus() == FriendshipStatus.PENDING){
                throw new GlobalException(ErrorCode.ALREADY_FRIENDS);
            }
            friendRepository.delete(friendEntity);
        }

        FriendEntity friendRequest = FriendEntity.builder()
                .author(user)
                .friend(friend)
                .status(FriendshipStatus.PENDING)
                .build();
        FriendEntity saveFriend = friendRepository.save(friendRequest);

        return mapper.toResponse(saveFriend);
    }
}

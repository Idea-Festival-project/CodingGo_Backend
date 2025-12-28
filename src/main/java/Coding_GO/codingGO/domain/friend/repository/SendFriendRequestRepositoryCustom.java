package Coding_GO.codingGO.domain.friend.repository;

import Coding_GO.codingGO.domain.friend.entity.FriendEntity;

import java.util.Optional;


public interface SendFriendRequestRepositoryCustom {
    Optional<FriendEntity> findByFriendShip(Long userId, Long friendId);
}

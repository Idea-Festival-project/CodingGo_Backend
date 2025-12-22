package Coding_GO.codingGO.domain.friend.repository;

import Coding_GO.codingGO.domain.friend.entity.FriendEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetSentFriendRequestRepositoryCustom {
    Page<FriendEntity> findSentRequestList(Long userId, Pageable pageable);
}

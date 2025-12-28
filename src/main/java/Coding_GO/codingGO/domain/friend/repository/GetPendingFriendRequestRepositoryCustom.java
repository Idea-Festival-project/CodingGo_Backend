package Coding_GO.codingGO.domain.friend.repository;

import Coding_GO.codingGO.domain.friend.entity.FriendEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface GetPendingFriendRequestRepositoryCustom {
    Page<FriendEntity> findPendingRequestList(Long userId, Pageable pageable);
}

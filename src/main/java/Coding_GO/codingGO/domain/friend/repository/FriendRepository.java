package Coding_GO.codingGO.domain.friend.repository;

import Coding_GO.codingGO.domain.friend.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface
FriendRepository extends JpaRepository<FriendEntity, Long>
        ,SendFriendRequestRepositoryCustom
        ,GetFriendListResponseRepositoryCustom
    ,GetSentFriendRequestRepositoryCustom
    ,GetPendingFriendRequestRepositoryCustom
{
}

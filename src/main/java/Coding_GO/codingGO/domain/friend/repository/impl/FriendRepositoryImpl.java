package Coding_GO.codingGO.domain.friend.repository.impl;

import Coding_GO.codingGO.domain.friend.entity.FriendEntity;
import Coding_GO.codingGO.domain.friend.entity.QFriendEntity;
import Coding_GO.codingGO.domain.friend.repository.SendFriendRequestRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class FriendRepositoryImpl implements SendFriendRequestRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<FriendEntity> findByFriendShip(Long userId, Long friendId) {
        QFriendEntity friendship = QFriendEntity.friendEntity;

        return Optional.ofNullable(
                queryFactory
                        .selectFrom(friendship)
                        .where(
                                friendship.author.userId.eq(userId)
                                        .and(friendship.friend.userId.eq(friendId))
                                        .or(friendship.author.userId.eq(friendId)
                                                .and(friendship.friend.userId.eq(userId)))
                        )
                        .fetchOne()
        );
    }
}

package Coding_GO.codingGO.domain.friend.repository.impl;

import Coding_GO.codingGO.domain.friend.data.constant.FriendshipStatus;
import Coding_GO.codingGO.domain.friend.entity.FriendEntity;
import Coding_GO.codingGO.domain.friend.entity.QFriendEntity;
import Coding_GO.codingGO.domain.friend.repository.GetPendingFriendRequestRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GetPendingFriendRequestRepositoryCustomImpl implements GetPendingFriendRequestRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FriendEntity> findPendingRequestList(Long userId, Pageable pageable) {
        QFriendEntity friend = QFriendEntity.friendEntity;

        List<FriendEntity> friendList = queryFactory
                .selectFrom(friend)
                .join(friend.author).fetchJoin()
                .where(
                        friend.friend.userId.eq(userId)
                                .and(friend.status.eq(FriendshipStatus.PENDING))
                )
                .orderBy(friend.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(friend.count())
                .from(friend)
                .where(
                        friend.friend.userId.eq(userId)
                                .and(friend.status.eq(FriendshipStatus.PENDING))
                )
                .fetchOne();

        return new PageImpl<>(friendList, pageable, total!=null ?  total : 0);
    }
}

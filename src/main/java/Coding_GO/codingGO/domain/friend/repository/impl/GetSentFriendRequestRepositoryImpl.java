package Coding_GO.codingGO.domain.friend.repository.impl;

import Coding_GO.codingGO.domain.friend.data.constant.FriendshipStatus;
import Coding_GO.codingGO.domain.friend.entity.FriendEntity;
import Coding_GO.codingGO.domain.friend.repository.GetSentFriendRequestRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GetSentFriendRequestRepositoryImpl implements GetSentFriendRequestRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FriendEntity> findSentRequestList(Long userId, Pageable pageable) {
        QFriendEntity friend = QFriendEntity.friendEntity;

        List<FriendEntity> friendList = queryFactory
                .selectFrom(friend)
                .join(friend.friend).fetchJoin()
                .where(
                        friend.author.userId.eq(userId)
                                .and(friend.status.eq(FriendshipStatus.PENDING))
                )
                .orderBy(friend.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchOne();

        Long total = queryFactory
                .select(friend.count())
                .from(friend)
                .where(
                        friend.author.userId.eq(userId)
                                .and(friend.status.eq(FriendshipStatus.PENDING))
                )
                .fetchOne();

        return new PageImpl<>(friendList, pageable, total!=null ?  total : 0);
    }
}

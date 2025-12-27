package Coding_GO.codingGO.domain.friend.repository.impl;

import Coding_GO.codingGO.domain.friend.data.constant.FriendshipStatus;
import Coding_GO.codingGO.domain.friend.entity.FriendEntity;
import Coding_GO.codingGO.domain.friend.entity.QFriendEntity;
import Coding_GO.codingGO.domain.friend.repository.GetFriendListResponseRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GetFriendListResponseRepositoryCustomImpl implements GetFriendListResponseRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FriendEntity> findPendingRequests(Long userId, Pageable pageable) {

        QFriendEntity friend = QFriendEntity.friendEntity;

        List<FriendEntity> content = queryFactory
                .selectFrom(friend)
                .join(friend.author).fetchJoin()
                .join(friend.friend).fetchJoin()
                .where(
                        friend.author.userId.eq(userId)
                                .or(friend.friend.userId.eq(userId)),
                        (friend.status.eq(FriendshipStatus.ACCEPTED))
                )
                .orderBy(friend.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(friend.count())
                .from(friend)
                .where(
                        friend.author.userId.eq(userId)
                                .or(friend.friend.userId.eq(userId)),
                        (friend.status.eq(FriendshipStatus.ACCEPTED))
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }
}

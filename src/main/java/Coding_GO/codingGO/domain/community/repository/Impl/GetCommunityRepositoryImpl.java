package Coding_GO.codingGO.domain.community.repository.Impl;

import Coding_GO.codingGO.domain.community.data.Community;

import Coding_GO.codingGO.domain.community.entity.QCommentEntity;
import Coding_GO.codingGO.domain.community.entity.QCommunityEntity;
import Coding_GO.codingGO.domain.community.repository.GetCommunityRepository;
import Coding_GO.codingGO.domain.user.entity.QUserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.provider.QueryComment;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GetCommunityRepositoryImpl implements GetCommunityRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Community> findAllCommunityWithCommentCount(Pageable pageable) {
        QCommunintyEntity communinty = QCommunityEntity.communityEntity;
        QCommentEntity comment = QCommentEntity.commentEntity;
        QUserEntity user = QUserEntity.userEntity;
        QProfileEntity profile = QProfileEntity.profileEntity;

        List<Community> communities = queryFactory
                .select(Projection.constructor(Community.class,
                        communinty.postId,
                        user.userId,
                        user.username,

                        Expression.cases()
                                .when(profile.nickname.isNotNull())
                                .then(profile.nickname)
                                .otherwise(user.username),

                        profile.profileImage,
                        communinty.category,
                        communinty.title,
                        communinty.content,
                        comment.commentId.count().intValue(),
                        communinty.createdAt
                        ))
                .from(communinty)
                .leftjoin(user).on(communinty.author.userId.eq(user.userId))
                .leftJoin(profile).on(user.userId.eq(profile.user.userId))
                .leftJoin(comment).on(comment.postId.eq(community.postId))
                .groupBy(
                        community.postId,
                        user.userId,
                        user.username,
                        profile.nickname,
                        profile.profileImage,
                        community.category,
                        community.title,
                        community.content,
                        community.createdAt
                )
                .orderBy(community.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long total = queryFactory
                .select(community.count())
                .from(community)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);

    }
}

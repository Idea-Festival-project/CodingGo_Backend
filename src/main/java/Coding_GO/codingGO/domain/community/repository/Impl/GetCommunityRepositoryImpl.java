package Coding_GO.codingGO.domain.community.repository.Impl;

import Coding_GO.codingGO.domain.community.data.Community;
import Coding_GO.codingGO.domain.community.entity.QCommentEntity;
import Coding_GO.codingGO.domain.community.entity.QCommunityEntity;
import Coding_GO.codingGO.domain.profile.entity.QUserProfileEntity;
import Coding_GO.codingGO.domain.community.repository.GetCommunityRepository;
import Coding_GO.codingGO.domain.user.entity.QUserEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GetCommunityRepositoryImpl implements GetCommunityRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Community> findAllCommunityWithCommentCount(Pageable pageable) {
        QCommunityEntity community = QCommunityEntity.communityEntity;
        QCommentEntity comment = QCommentEntity.commentEntity;
        QUserEntity user = QUserEntity.userEntity;
        QUserProfileEntity profile = QUserProfileEntity.userProfileEntity;

        List<Community> communities = queryFactory
                .select(Projections.constructor(Community.class,
                        community.postId,
                        user.userId,
                        // CaseBuilder: 프로필 닉네임이 있으면 사용, 없으면 유저 닉네임 사용
                        new CaseBuilder()
                                .when(profile.nickname.isNotNull())
                                .then(profile.nickname)
                                .otherwise(user.nickname),
                        profile.profileImage,
                        community.category,
                        community.title,
                        community.content,
                        comment.count(),
                        community.createdAt
                ))
                .from(community)
                .leftJoin(user).on(community.author.eq(user))
                .leftJoin(profile).on(profile.user.eq(user))
                .leftJoin(comment).on(comment.post.eq(community))
                .groupBy(
                        community.postId,
                        user.userId,
                        user.nickname,
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

        return new PageImpl<>(communities, pageable, total != null ? total : 0L);
    }
}
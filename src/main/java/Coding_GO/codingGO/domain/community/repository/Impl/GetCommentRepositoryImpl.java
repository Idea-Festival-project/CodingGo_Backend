package Coding_GO.codingGO.domain.community.repository.Impl;

import Coding_GO.codingGO.domain.community.entity.CommentEntity;
import Coding_GO.codingGO.domain.community.repository.GetCommentRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static Coding_GO.codingGO.domain.community.entity.QCommentEntity.commentEntity;
import static Coding_GO.codingGO.domain.community.entity.QUserEntity.userEntity;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GetCommentRepositoryImpl implements GetCommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommentEntity> findCommentsByPostIdWithAuthor(Long postId) {
        return queryFactory
                .selectFrom(commentEntity)
                .join(commentEntity.author, userEntity).fetchJoin()
                .where(
                        commentEntity.post.postId.eq(postId),
                        commentEntity.is_deleted.eq(false)
                )
                .orderBy(
                        commentEntity.parentComment.commentId.asc().nullsFirst(),
                        commentEntity.create_at.asc()
                )
                .fetch();
    }
}


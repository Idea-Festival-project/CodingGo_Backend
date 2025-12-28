package Coding_GO.codingGO.domain.community.repository;

import Coding_GO.codingGO.domain.community.entity.CommentEntity;

import java.util.List;

public interface GetCommentRepositoryCustom {
    List<CommentEntity> findCommentsByPostIdWithAuthor(Long postId);
}


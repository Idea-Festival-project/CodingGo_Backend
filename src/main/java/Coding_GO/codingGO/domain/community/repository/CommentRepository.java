package Coding_GO.codingGO.domain.community.repository;

import Coding_GO.codingGO.domain.community.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface
CommentRepository extends JpaRepository<CommentEntity, Long>, GetCommentRepositoryCustom {
}
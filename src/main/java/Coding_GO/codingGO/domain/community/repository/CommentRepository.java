package Coding_GO.codingGO.domain.community.repository;

import Coding_GO.codingGO.domain.community.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query(value = """
        WITH RECURSIVE commentTable AS (
            SELECT 
                c.*,
                0 AS depth,
                CAST(c.comment_id AS CHAR) AS path_order
            FROM comment c
            WHERE c.post_id = :postId AND c.parent_comment_id IS NULL
            
            UNION ALL
            
            SELECT
                c.*,
                ch.depth + 1 AS depth,
                CONCAT(ch.path_order, '.', CAST(c.comment_id AS CHAR)) AS path_order
            FROM comment c
            INNER JOIN commentTable ch ON c.parent_comment_id = ch.comment_id
        )
        SELECT
            ch.*,
            a.user_id, a.username, a.profile_image_url
        FROM commentTable ch
        JOIN user a ON ch.user_id = a.user_id
        ORDER BY ch.path_order;
        
""", nativeQuery = true)
    List<CommentEntity> findCommentsByPostId(@Param("postId") Long postId);
}
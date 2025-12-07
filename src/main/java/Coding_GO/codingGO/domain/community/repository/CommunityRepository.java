package Coding_GO.codingGO.domain.community.repository;

import Coding_GO.codingGO.domain.community.entity.CommunityEntity;
import Coding_GO.codingGO.domain.community.presentation.data.projection.CommentCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity,Long> {

    @Query(
            value = "SELECT c FROM CommunityEntity c " +
                    "JOIN FETCH c.author a " +
                    "LEFT JOIN FETCH a.profile " +
                    "ORDER BY c.createdAt DESC",

            countQuery = "SELECT COUNT(c) FROM CommunityEntity c"
    )
    Page<CommunityEntity> findAllCommunity(Pageable pageable);

    @Query("SELECT c.postId, COUNT(cm) FROM CommunityEntity c " +
            "LEFT JOIN c.comments cm " +
            "GROUP BY c.postId")
    List<CommentCount> countCommunityByPost();
}

package Coding_GO.codingGO.domain.community.repository;

import Coding_GO.codingGO.domain.community.data.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetCommunityRepository {
    Page<Community> findAllCommunityWithCommentCount(Pageable pageable);
}

package Coding_GO.codingGO.domain.community.repository;

import Coding_GO.codingGO.domain.community.entity.CommunityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CommunityRepository extends
        JpaRepository<CommunityEntity, Long>,
        GetCommunityRepository {
}



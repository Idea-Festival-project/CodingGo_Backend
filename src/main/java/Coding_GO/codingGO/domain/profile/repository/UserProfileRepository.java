package Coding_GO.codingGO.domain.profile.repository;

import Coding_GO.codingGO.domain.profile.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity,Long> {
    Optional<UserProfileEntity> findByNickname(String nickname);
    Optional<UserProfileEntity> findByUserUserId(Long userId);
}


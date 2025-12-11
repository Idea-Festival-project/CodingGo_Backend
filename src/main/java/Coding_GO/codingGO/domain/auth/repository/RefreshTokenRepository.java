package Coding_GO.codingGO.domain.auth.repository;

import Coding_GO.codingGO.domain.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    /**
     * 이메일로 Refresh Token 조회
     */
    Optional<RefreshToken> findByEmail(String email);

    /**
     * 토큰 값으로 Refresh Token 조회
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * 이메일로 Refresh Token 삭제
     */
    void deleteByEmail(String email);

    /**
     * 이메일로 Refresh Token 존재 여부 확인
     */
    boolean existsByEmail(String email);
}
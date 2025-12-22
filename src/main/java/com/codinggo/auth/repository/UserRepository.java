package com.codinggo.auth.repository;

import com.codinggo.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);  // ← 이 메소드가 없어서 컴파일 오류 발생

    // (선택) 더 효율적인 방법 - 한 번의 쿼리로 이메일 또는 사용자명 중복 확인
    boolean existsByEmailOrUsername(String email, String username);
}
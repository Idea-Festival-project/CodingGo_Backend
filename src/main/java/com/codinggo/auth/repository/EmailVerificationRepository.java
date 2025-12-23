package com.codinggo.auth.repository;

import com.codinggo.auth.entity.EmailVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerificationEntity, Long> {
    Optional<EmailVerificationEntity> findByEmailAndIsVerifiedFalse(String email);
    Optional<EmailVerificationEntity> findByEmail(String email);
    void deleteByEmailAndIsVerifiedTrue(String email);
}
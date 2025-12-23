package com.codinggo.auth.service.impl;

import com.codinggo.auth.entity.EmailVerificationEntity;
import com.codinggo.auth.exception.AuthException;
import com.codinggo.auth.repository.EmailVerificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class VerifyEmailServiceImpl {

    private final EmailVerificationRepository emailVerificationRepository;

    public boolean execute(String email, String code) {
        // 1. 미인증 상태의 이메일 인증 기록 조회
        EmailVerificationEntity verification = emailVerificationRepository
                .findByEmailAndIsVerifiedFalse(email)
                .orElseThrow(() -> {
                    log.warn("인증 시도 - 인증 기록 없음: {}", email);
                    return new AuthException("이메일 인증을 요청해주세요.");
                });

        // 2. 만료 확인
        if (verification.isExpired()) {
            log.warn("인증 시도 - 인증번호 만료: {}", email);
            emailVerificationRepository.delete(verification);
            throw new AuthException("인증번호가 만료되었습니다. 다시 요청해주세요.");
        }

        // 3. 최대 시도 횟수 확인
        if (verification.isMaxAttemptsExceeded()) {
            log.warn("인증 시도 - 최대 시도 횟수 초과: {}", email);
            emailVerificationRepository.delete(verification);
            throw new AuthException("인증 시도 횟수를 초과했습니다. 다시 요청해주세요.");
        }

        // 4. 인증번호 확인
        if (!verification.getVerificationCode().equals(code)) {
            verification.incrementAttempt();
            emailVerificationRepository.save(verification);
            log.warn("인증 시도 - 잘못된 인증번호: {}, 시도 횟수: {}", email, verification.getAttemptCount());
            return false;
        }

        // 5. 인증 완료 처리
        verification.verify();
        emailVerificationRepository.save(verification);
        log.info("이메일 인증 완료: {}", email);

        return true;
    }
}
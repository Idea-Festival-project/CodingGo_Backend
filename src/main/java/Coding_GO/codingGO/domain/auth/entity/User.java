package Coding_GO.codingGO.domain.auth.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)  // 자동 날짜 업데이트
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, unique = true, length = 50)
    private String nickname;

    @Column(length = 255)
    private String profileImageUrl;

    @Column(length = 500)
    private String introduction;

    @Column(length = 100)
    private String resetPasswordCode;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ====== 비즈니스 로직 메서드들 ======

    /**
     * 프로필 수정
     */
    public void updateProfile(String nickname, String profileImageUrl, String introduction) {
        if (nickname != null && !nickname.isBlank()) {
            this.nickname = nickname;
        }
        if (profileImageUrl != null) {
            this.profileImageUrl = profileImageUrl;
        }
        if (introduction != null) {
            this.introduction = introduction;
        }
    }

    /**
     * 비밀번호 변경
     */
    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    /**
     * 비밀번호 재설정 코드 업데이트
     */
    public void updateResetPasswordCode(String code) {
        this.resetPasswordCode = code;
    }

    /**
     * 비밀번호 재설정 코드 제거
     */
    public void clearResetPasswordCode() {
        this.resetPasswordCode = null;
    }
}
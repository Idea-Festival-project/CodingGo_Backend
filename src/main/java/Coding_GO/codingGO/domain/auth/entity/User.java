package Coding_GO.codingGO.domain.auth.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;          // 로그인에 사용할 이메일

    @Column(nullable = false, length = 255)
    private String password;       // 암호화된 비밀번호

    @Column(nullable = false, unique = true, length = 50)
    private String nickname;

    @Column(length = 255)
    private String profileImageUrl;

    @Column(length = 255)
    private String introduction;

    // 비밀번호 변경/메일 인증 등에서 쓸 수 있는 코드
    @Column(length = 100)
    private String resetPasswordCode;

    // ====== 비즈니스 로직 메서드들 ======

    public void updateProfile(String nickname, String profileImageUrl, String introduction) {
        if (nickname != null) {
            this.nickname = nickname;
        }
        if (profileImageUrl != null) {
            this.profileImageUrl = profileImageUrl;
        }
        if (introduction != null) {
            this.introduction = introduction;
        }
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void updateResetPasswordCode(String code) {
        this.resetPasswordCode = code;
    }
}

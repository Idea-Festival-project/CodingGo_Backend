package Coding_GO.codingGO.domain.user.entity;

import Coding_GO.codingGO.domain.profile.entity.UserProfileEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;

    private LocalDateTime lastSyncAt;

    private String bojNickname;

    @Builder.Default
    private Long point = 0L;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserProfileEntity profile;

    public void addPoint(int points) {
        this.point += points;
    }
    public void updateLastSyncAt() {
        this.lastSyncAt = LocalDateTime.now();
    }
}

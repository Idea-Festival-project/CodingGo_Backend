package Coding_GO.codingGO.domain.profile.entity;

import Coding_GO.codingGO.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserProfileEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true) // FK 주인
    private UserEntity user;

    @Column(nullable = false, unique = true, length = 100)
    private String nickname;

    @Lob @Column(columnDefinition = "LONGBLOB")
    private byte[] profileImage;

    @Column(columnDefinition = "TEXT")
    private String bio;

    public void updateBio(String bio, byte[] image) {
        this.bio = bio;
        if (image != null) this.profileImage = image;
    }
}

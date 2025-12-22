package com.codinggo.profile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profile")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "pio", length = 250)
    private String pio;

    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void changePio(String newPio) {
        this.pio = newPio;
    }
}
package com.codinggo.profile.presentation.data.response;

import com.codinggo.profile.entity.ProfileEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {

    private String nickname;

    private String bio;

    @JsonProperty("profile_image_url")
    private String profileImageUrl;

    private Stats stats;

    @JsonProperty("is_friend")
    private Boolean isFriend;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Stats {
        @JsonProperty("total_problems")
        private Integer totalProblems;

        @JsonProperty("completed_achievements")
        private Integer completedAchievements;

        @JsonProperty("current_streak")
        private Integer currentStreak;

        @JsonProperty("total_points")
        private Integer totalPoints;
    }

    /**
     * 내 프로필용 ProfileResponse (stats 포함)
     */
    public static ProfileResponse from(ProfileEntity profile) {
        return ProfileResponse.builder()
                .nickname(profile.getNickname())
                .bio(profile.getBio())
                .profileImageUrl(profile.getProfileImageUrl())
                .stats(Stats.builder()
                        .totalProblems(0)
                        .completedAchievements(0)
                        .currentStreak(0)
                        .totalPoints(0)
                        .build())
                .build();
    }

    /**
     * 다른 사용자 프로필용 ProfileResponse (isFriend 포함)
     */
    public static ProfileResponse fromWithFriend(ProfileEntity profile, Boolean isFriend) {
        return ProfileResponse.builder()
                .nickname(profile.getNickname())
                .bio(profile.getBio())
                .profileImageUrl(profile.getProfileImageUrl())
                .stats(Stats.builder()
                        .totalProblems(0)
                        .completedAchievements(0)
                        .build())
                .isFriend(isFriend)
                .build();
    }
}
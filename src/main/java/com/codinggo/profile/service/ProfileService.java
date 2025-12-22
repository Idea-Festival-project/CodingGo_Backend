package com.codinggo.profile.service;

import com.codinggo.profile.presentation.data.request.UpdateProfileRequest;
import com.codinggo.profile.presentation.data.response.ProfileResponse;

public interface ProfileService {

    ProfileResponse getProfile(Long userId);

    void updateProfile(Long userId, UpdateProfileRequest request);

    ProfileResponse getUserProfile(Long userId);
}
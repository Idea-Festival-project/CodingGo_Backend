package Coding_GO.codingGO.domain.profile.service;

import Coding_GO.codingGO.domain.profile.presentation.data.response.ProfileResponse;
import Coding_GO.codingGO.domain.user.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserProfileService {
    ProfileResponse getProfileById(Long userId);
    ProfileResponse getProfileByNickname(String nickname);
    ProfileResponse convertToResponse(UserEntity user);
    void updateProfile(Long userId, String bio, MultipartFile image) throws IOException;
}



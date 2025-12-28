package Coding_GO.codingGO.domain.profile.service.impl;

import Coding_GO.codingGO.domain.profile.entity.UserProfileEntity;
import Coding_GO.codingGO.domain.profile.presentation.data.response.ProfileResponse;
import Coding_GO.codingGO.domain.profile.repository.UserProfileRepository;
import Coding_GO.codingGO.domain.profile.service.UserProfileService;
import Coding_GO.codingGO.domain.user.entity.UserEntity;
import Coding_GO.codingGO.domain.user.repository.UserRepository;
import Coding_GO.codingGO.global.exception.ErrorCode;
import Coding_GO.codingGO.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserProfileServiceImpl implements UserProfileService {


    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    @Override
    public ProfileResponse getProfileById(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
        return convertToResponse(user);
    }

    @Override
    public ProfileResponse getProfileByNickname(String nickname) {
        UserProfileEntity profile = userProfileRepository.findByNickname(nickname)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_NICKNAME));
        return convertToResponse(profile.getUser());
    }

    @Override
    @Transactional
    public void updateProfile(Long userId, String bio, MultipartFile image) throws IOException {
        UserProfileEntity profile = userProfileRepository.findByUserUserId(userId)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));


        byte[] imageBytes = (image != null && !image.isEmpty()) ? image.getBytes() : profile.getProfileImage();

        profile.updateBio(bio, imageBytes);
    }

    @Override
    public ProfileResponse convertToResponse(UserEntity user) {
        UserProfileEntity profile = user.getProfile();
        if (profile == null) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        String base64Image = (profile.getProfileImage() != null) ?
                Base64.getEncoder().encodeToString(profile.getProfileImage()) : null;

        return new ProfileResponse(
                profile.getNickname(),
                profile.getBio(),
                user.getPoint(),
                base64Image
        );
    }
}
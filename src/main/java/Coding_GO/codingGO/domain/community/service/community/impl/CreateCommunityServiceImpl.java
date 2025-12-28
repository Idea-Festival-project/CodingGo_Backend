package Coding_GO.codingGO.domain.community.service.community.impl;

import Coding_GO.codingGO.domain.community.data.Community;
import Coding_GO.codingGO.domain.community.entity.CommunityEntity;
import Coding_GO.codingGO.domain.community.mapper.communirty.CreateCommunityMapper;
import Coding_GO.codingGO.domain.community.presentation.data.request.community.CreateCommunityRequest;
import Coding_GO.codingGO.domain.community.presentation.data.response.community.CreateCommunityResponse;
import Coding_GO.codingGO.domain.community.repository.CommunityRepository;
import Coding_GO.codingGO.domain.community.service.community.CreateCommunityService;

import Coding_GO.codingGO.domain.user.entity.UserEntity;
import Coding_GO.codingGO.domain.user.repository.UserRepository;
import Coding_GO.codingGO.global.exception.ErrorCode;
import Coding_GO.codingGO.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CreateCommunityServiceImpl implements CreateCommunityService {


    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final CreateCommunityMapper mapper;

    @Override
    @Transactional
    @CacheEvict(
            value = "communityList",
            allEntries = true
    )
    public CreateCommunityResponse execute(CreateCommunityRequest request, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND_EXCEPTION));

        CommunityEntity community = mapper.createEntity(
                request.category(),
                request.content(),
                user
        );
        CommunityEntity saved = communityRepository.save(community);

        Community dto = mapper.toDto(saved);

        return CreateCommunityResponse.from(dto);
    }

}

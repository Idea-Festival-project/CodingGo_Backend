package Coding_GO.codingGO.domain.community.service.impl;

import Coding_GO.codingGO.domain.community.data.Community;
import Coding_GO.codingGO.domain.community.entity.CommunityEntity;
import Coding_GO.codingGO.domain.community.mapper.CreateCommunityMapper;
import Coding_GO.codingGO.domain.community.presentation.data.request.CreateCommunityRequest;
import Coding_GO.codingGO.domain.community.presentation.data.response.CreateCommunityResponse;
import Coding_GO.codingGO.domain.community.repository.CommunityRepository;
import Coding_GO.codingGO.domain.community.service.CreateCommunityService;

import Coding_GO.codingGO.global.exception.ErrorCode;
import Coding_GO.codingGO.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CreateCommunityServiceImpl implements CreateCommunityService {

    private final CommunityRepository CommunityRepository;
    private final UserRepository userRepository;
    private final CreateCommunityMapper mapper;

    @Override
    @Transactional
    @CacheEvict(
            value = "communityList",
            allEntries = true
    )
    public CreateCommunityResponse execute(CreateCommunityRequest request, Long userId) {
        UserEntity user = userRespository.findBy(userId)
                .orElseThrow(()-> new GlobalException(ErrorCode.USER_NOT_FOUND_EXCEPTION));

        CommunityEntity community = mapper.createEntity(
                request.category(),
                request.content(),
                user
        );
        CommunityEntity saved = CommunityRepository.save(community);

        Community dto = mapper.toDto(saved);

        return CreateCommunityResponse.from(dto);
    }
}

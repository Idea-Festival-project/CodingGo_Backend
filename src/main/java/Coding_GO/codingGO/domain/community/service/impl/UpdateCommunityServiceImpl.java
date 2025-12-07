package Coding_GO.codingGO.domain.community.service.impl;


import Coding_GO.codingGO.domain.community.data.Community;
import Coding_GO.codingGO.domain.community.entity.CommunityEntity;
import Coding_GO.codingGO.domain.community.mapper.CreateCommunityMapper;
import Coding_GO.codingGO.domain.community.presentation.data.request.CreateCommunityRequest;
import Coding_GO.codingGO.domain.community.presentation.data.request.UpdateCommunityRequest;
import Coding_GO.codingGO.domain.community.presentation.data.response.CreateCommunityResponse;
import Coding_GO.codingGO.domain.community.repository.CommunityRepository;
import Coding_GO.codingGO.domain.community.service.UpdateCommunityService;
import Coding_GO.codingGO.global.exception.ErrorCode;
import Coding_GO.codingGO.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateCommunityServiceImpl implements UpdateCommunityService {

    private final CommunityRepository communityRepository;
    private final CreateCommunityMapper mapper;

    @Override
    @Transactional
    public CreateCommunityResponse execute(Long postId, UpdateCommunityRequest request, Long user_id) {

        CommunityEntity community = communityRepository.findById(postId)
                .orElseThrow(()-> new GlobalException(ErrorCode.COMMUNITY_NOT_FOUND_EXCEPTION));

        if(!community.getAuthor().getUserId().equals(user_id)) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND_EXCEPTION);
        }

        community.setContent(request.content());
        community.setCategory(request.category());

        Community dto = mapper.toDto(community);

        return CreateCommunityResponse.from(dto);
    }
}

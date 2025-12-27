package Coding_GO.codingGO.domain.community.service.community.impl;


import Coding_GO.codingGO.domain.community.entity.CommunityEntity;
import Coding_GO.codingGO.domain.community.repository.CommunityRepository;
import Coding_GO.codingGO.domain.community.service.community.DeleteCommunityService;
import Coding_GO.codingGO.global.exception.ErrorCode;
import Coding_GO.codingGO.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteCommunityServiceImpl implements DeleteCommunityService {
    private final CommunityRepository communityRepository;

    @Override
    @CacheEvict(value = "communityList", allEntries = true)
    @Transactional
    public void execute(Long postId) {
        CommunityEntity community = communityRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(ErrorCode.COMMUNITY_NOT_FOUND_EXCEPTION));

        communityRepository.delete(community);
    }
}

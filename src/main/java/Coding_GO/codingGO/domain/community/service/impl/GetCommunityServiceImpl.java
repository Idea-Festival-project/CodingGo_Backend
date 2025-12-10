package Coding_GO.codingGO.domain.community.service.impl;

import Coding_GO.codingGO.domain.community.data.Community;
import Coding_GO.codingGO.domain.community.mapper.GetCommunityMapper;
import Coding_GO.codingGO.domain.community.presentation.data.response.GetCommunityListResponse;
import Coding_GO.codingGO.domain.community.repository.CommunityRepository;
import Coding_GO.codingGO.domain.community.service.GetCommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetCommunityServiceImpl implements GetCommunityService {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;

    private final CommunityRepository communityRepository;
    private final GetCommunityMapper mapper;

    @Override
    @Cacheable(
            value = "communityList",
            key = "'page:' + #page + ':limit:' + #limit"
    )

    public GetCommunityListResponse execute(Integer page, Integer limit) {
        int pageNumber = (page == null || page < 1) ? DEFAULT_PAGE : page;
        int pageSize = (limit == null || limit < 1) ? DEFAULT_PAGE_SIZE : limit;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Community> communityPage = communityRepository.findAllCommunityWithCommentCount(pageable);

        return new GetCommunityListResponse(communityPage);

    }
}

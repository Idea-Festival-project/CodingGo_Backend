package Coding_GO.codingGO.domain.community.service.impl;

import Coding_GO.codingGO.domain.community.data.Community;
import Coding_GO.codingGO.domain.community.entity.CommunityEntity;
import Coding_GO.codingGO.domain.community.mapper.CreateCommunityMapper;
import Coding_GO.codingGO.domain.community.mapper.GetCommunityMapper;
import Coding_GO.codingGO.domain.community.presentation.data.projection.CommentCount;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GetCommunityServiceImpl implements GetCommunityService {

    private final CommunityRepository communityRepository;
    private final GetCommunityMapper mapper;


    @Override
    @Cacheable(
            value = "communityList",
            key = "'page:' + #page + ':limit:' + #limit\"
    )
    @Transactional(readOnly = true)
    public GetCommunityListResponse execute(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(
                page,
                limit
        );
        Page<Community> communityPage =
                communityRepository.findAllCommunity(pageable)
                        .map(mapper::toDto);

        Map<Long,Long> commentCount = communityRepository.countCommunityByPost()
                .stream()
                .collect(Collectors.toMap(CommentCount::postId, CommentCount::commentCount));

        communityPage.getContent().forEach(c -> {
            c.setCommentCount(commentCount.getOrDefault(c.getPostId(), 0L).intValue());
        });
        return mapper.toCommunityListResponse(communityPage);
    }
}

package Coding_GO.codingGO.domain.friend.service.impl;

import Coding_GO.codingGO.domain.friend.entity.FriendEntity;
import Coding_GO.codingGO.domain.friend.mapper.GetFriendListMapper;
import Coding_GO.codingGO.domain.friend.presentation.data.request.FriendItem;
import Coding_GO.codingGO.domain.friend.presentation.data.response.GetFriendListResponse;
import Coding_GO.codingGO.domain.friend.repository.FriendRepository;
import Coding_GO.codingGO.domain.friend.service.GetFriendListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetFriendListServiceImpl implements GetFriendListService {

    private final FriendRepository friendRepository;
    private final GetFriendListMapper mapper;

    @Override
    public GetFriendListResponse execute(Long userId, int page, int limit) {

        Pageable pageable = PageRequest.of(page, limit);

        Page<FriendEntity> friendPage = friendRepository.findPendingRequests(userId, pageable);

        List<FriendItem> friendItems = mapper.toFriendItemDtoList(
                friendPage.getContent(),
                userId
        );

        return GetFriendListResponse.builder()
                .data(GetFriendListResponse.Friend.builder()
                        .friend(friendItems)
                        .totalCount((int) friendPage.getTotalElements())
                        .currentPage(page)
                        .totalPage(friendPage.getTotalPages())
                        .build())
                .build();
    }
}

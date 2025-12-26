package Coding_GO.codingGO.domain.friend.service.impl;

import Coding_GO.codingGO.domain.friend.data.GetPendingFriendRequestResponseItemDto;
import Coding_GO.codingGO.domain.friend.entity.FriendEntity;
import Coding_GO.codingGO.domain.friend.mapper.GetPendingRequestListMapper;
import Coding_GO.codingGO.domain.friend.mapper.GetSentFriendRequestListMapper;
import Coding_GO.codingGO.domain.friend.presentation.data.response.GetPendingFriendRequestResponse;
import Coding_GO.codingGO.domain.friend.repository.FriendRepository;
import Coding_GO.codingGO.domain.friend.service.GetSentFriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GetSentFriendRequestServiceImpl implements GetSentFriendRequestService {
    private final FriendRepository friendRepository;
    private final GetSentFriendRequestListMapper mapper;

    @Override
    public GetPendingFriendRequestResponse execute(Long userId, int page, int limit) {

        Pageable pageable = PageRequest.of(page, limit);

        Page<FriendEntity> requestPage = friendRepository.findSentRequestList(userId,pageable);

        List<GetPendingFriendRequestResponseItemDto> requestItem =
                mapper.toGetSentFriendRequestResponseItemDtoList(requestPage.getContent());

        GetPendingFriendRequestResponse.GetFriendRequestResponse data =
                GetPendingFriendRequestResponse.GetFriendRequestResponse.builder()
                        .items(requestItem)
                        .currentPage((int) requestPage.getTotalElements())
                        .currentPage(page)
                        .totalPage(requestPage.getTotalPages())
                        .build();

        return GetPendingFriendRequestResponse.builder()
                .data(data).build();
    }
    }
}

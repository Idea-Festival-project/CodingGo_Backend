package Coding_GO.codingGO.domain.friend.service;

import Coding_GO.codingGO.domain.friend.presentation.data.response.GetFriendListResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GetFriendListService {
    GetFriendListResponse execute(Long userId, int page,int limit);
}

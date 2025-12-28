package Coding_GO.codingGO.domain.community.service;

import Coding_GO.codingGO.domain.community.presentation.data.response.GetCommunityListResponse;

public interface GetCommunityService {
    GetCommunityListResponse execute(Integer page, Integer limit);
}

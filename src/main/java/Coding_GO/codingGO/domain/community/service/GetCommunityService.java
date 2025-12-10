package Coding_GO.codingGO.domain.community.service;

import Coding_GO.codingGO.domain.community.presentation.data.response.CreateCommunityResponse;
import Coding_GO.codingGO.domain.community.presentation.data.response.GetCommunityListResponse;

import java.util.List;

public interface GetCommunityService {
    GetCommunityListResponse execute(Integer page, Integer limit);
}

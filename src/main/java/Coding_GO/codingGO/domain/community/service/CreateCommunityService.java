package Coding_GO.codingGO.domain.community.service;

import Coding_GO.codingGO.domain.community.presentation.data.request.CreateCommunityRequest;
import Coding_GO.codingGO.domain.community.presentation.data.response.CreateCommunityResponse;

public interface CreateCommunityService {
    CreateCommunityResponse execute(CreateCommunityRequest request, Long userId);
}

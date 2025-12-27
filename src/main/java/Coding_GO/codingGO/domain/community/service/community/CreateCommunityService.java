package Coding_GO.codingGO.domain.community.service.community;

import Coding_GO.codingGO.domain.community.presentation.data.request.community.CreateCommunityRequest;
import Coding_GO.codingGO.domain.community.presentation.data.response.community.CreateCommunityResponse;

public interface CreateCommunityService {
    CreateCommunityResponse execute(CreateCommunityRequest request, Long userId);
}

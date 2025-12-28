package Coding_GO.codingGO.domain.community.service.community;

import Coding_GO.codingGO.domain.community.presentation.data.request.community.UpdateCommunityRequest;
import Coding_GO.codingGO.domain.community.presentation.data.response.community.CreateCommunityResponse;

public interface UpdateCommunityService {
    CreateCommunityResponse execute(Long postId, UpdateCommunityRequest request , Long user_id);

}


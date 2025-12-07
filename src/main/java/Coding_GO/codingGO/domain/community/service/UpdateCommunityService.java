package Coding_GO.codingGO.domain.community.service;

import Coding_GO.codingGO.domain.community.presentation.data.request.CreateCommunityRequest;
import Coding_GO.codingGO.domain.community.presentation.data.request.UpdateCommunityRequest;
import Coding_GO.codingGO.domain.community.presentation.data.response.CreateCommunityResponse;

public interface UpdateCommunityService {
    CreateCommunityResponse execute(Long postId, UpdateCommunityRequest request , Long user_id);

}

package Coding_GO.codingGO.domain.community.presentation.data.response;

import Coding_GO.codingGO.domain.community.data.Community;
import Coding_GO.codingGO.domain.community.presentation.data.AuthorInfo;
import Coding_GO.codingGO.domain.community.presentation.data.CommunityData;
import Coding_GO.codingGO.domain.community.presentation.data.CommunityInfo;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public record GetCommunityListResponse(
    String status,
    CommunityData community
) {}

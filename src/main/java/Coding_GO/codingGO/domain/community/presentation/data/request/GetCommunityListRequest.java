package Coding_GO.codingGO.domain.community.presentation.data.request;

public record GetCommunityListRequest(
        Integer page,
        Integer limit
) {
    public GetCommunityListRequest{
        if(page == null || page < 1){
            page= 0;
        }
        if(limit == null || limit < 1){
            limit = 10;
        }
    }
}

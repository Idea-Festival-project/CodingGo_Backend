package Coding_GO.codingGO.domain.profile.presentation.data.response;

public record ProfileResponse(
        String nickname,
        String bio,
        Long point,
        String profileImage
) {

}

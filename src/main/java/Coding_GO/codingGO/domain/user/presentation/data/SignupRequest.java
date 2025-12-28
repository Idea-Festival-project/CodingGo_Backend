package Coding_GO.codingGO.domain.user.presentation.data;

public record SignupRequest(
        String email,
        String password,
        String nickname,
        String bojNickname,
        String code
) {

}

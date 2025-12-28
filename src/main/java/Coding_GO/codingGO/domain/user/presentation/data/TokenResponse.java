package Coding_GO.codingGO.domain.user.presentation.data;

public record TokenResponse(
        String accessToken, String refreshToken, String grantType
) {
}


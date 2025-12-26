package Coding_GO.codingGO.domain.user.service;

import Coding_GO.codingGO.domain.user.presentation.data.LoginRequest;
import Coding_GO.codingGO.domain.user.presentation.data.SignupRequest;
import Coding_GO.codingGO.domain.user.presentation.data.TokenResponse;

public interface AuthService {
    void signup(SignupRequest request);
    TokenResponse login(LoginRequest request);
    void logout(String accessToken);
}

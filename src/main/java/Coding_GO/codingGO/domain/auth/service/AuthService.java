package Coding_GO.codingGO.domain.auth.service;

import Coding_GO.codingGO.domain.auth.dto.LoginRequest;
import Coding_GO.codingGO.domain.auth.dto.LoginResponse;
import Coding_GO.codingGO.domain.auth.dto.SignUpRequest;
import Coding_GO.codingGO.domain.auth.dto.UserResponse;

public interface AuthService {

    UserResponse signUp(SignUpRequest request);

    LoginResponse login(LoginRequest request);
}

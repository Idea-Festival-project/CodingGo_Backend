package Coding_GO.codingGO.domain.auth.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String email;
    private String password;
    private String nickname;
}

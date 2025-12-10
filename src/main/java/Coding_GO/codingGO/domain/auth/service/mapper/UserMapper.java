package Coding_GO.codingGO.domain.auth.service.mapper;

import Coding_GO.codingGO.domain.auth.dto.SignUpRequest;
import Coding_GO.codingGO.domain.auth.dto.UserResponse;
import Coding_GO.codingGO.domain.auth.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // SignUpRequest + 암호화된 비밀번호 → User 엔티티 생성
    public User toEntity(SignUpRequest request, String encodedPassword) {
        return User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .nickname(request.getNickname())
                .build();
    }

    // User 엔티티 → UserResponse DTO 변환
    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}

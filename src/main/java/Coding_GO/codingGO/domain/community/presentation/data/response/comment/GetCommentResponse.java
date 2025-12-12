package Coding_GO.codingGO.domain.community.presentation.data.response.comment;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetCommentResponse {
    private Long commentId;
    private Long userId;
    private String username;
    private String profileImageUrl;
    private String content;
    private Long parentCommentId;
    private LocalDateTime createdAt;
    private List<GetCommentResponse> babyComment;
}

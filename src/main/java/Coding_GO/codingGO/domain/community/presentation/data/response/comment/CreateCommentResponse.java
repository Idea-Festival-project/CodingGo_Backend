package Coding_GO.codingGO.domain.community.presentation.data.response.comment;

import java.time.LocalDateTime;

public record CreateCommentResponse(
        Long commentId,
        String content,
        LocalDateTime created_At
) {}


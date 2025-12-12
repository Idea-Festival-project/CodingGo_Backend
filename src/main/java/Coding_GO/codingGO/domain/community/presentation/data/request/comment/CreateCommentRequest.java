package Coding_GO.codingGO.domain.community.presentation.data.request.comment;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCommentRequest(
        String content,
        @JsonProperty("parent_comment_id")
        Long parentCommentId
) {}

package Coding_GO.codingGO.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // auth
    USER_ALREADY_EXISTS_EXCEPTION(400, "이미 사용 중인 이메일입니다."),
    USER_ALREADY_EXISTS_USERNAME(400, "이미 사용 중인 사용자 이름입니다."),
    INVALID_TOKEN_EXCEPTION(400, "유효하지 않은 토큰입니다."),

    // community
    COMMUNITY_NOT_FOUND_EXCEPTION(404,"게시글을 찾을 수 없습니다."),
    USER_NOT_AUTHORIZED(403, "이 글을 수정할 권한이 없습니다."),
    INVALID_CATEGORY_EXCEPTION(400, "잘못된 카테고리입니다."),

    // user
    USER_NOT_FOUND_EXCEPTION(404,"사용자를 찾을 수 없습니다."),
    UNAUTHORIZED_EXCEPTION(403, "권한이 없습니다."),

    // comment
    NOT_FOUND_COMMENT(404, "댓글을 찾을 수 없습니다."),
    NOT_FOUND_PARENT_COMMENT(404, "부모댓글을 찾을 수 없습니다."),
    PARENT_COMMENT_IS_DELETED(400, "삭제된 댓글에는 대댓글을 작성할 수 없습니다."),
    CANNOT_UPDATE_DELETED_COMMENT(400,"댓글을 수정 할 수 없습니다.");

    private final int status;
    private final String message;
}
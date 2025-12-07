package Coding_GO.codingGO.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //community
    COMMUNITY_NOT_FOUND_EXCEPTION(404,"게시글을 찾을 수 없습니다."),
    USER_NOT_AUTHORIZED(403, "이 글을 수정할 권한이 없습니다."),
    INVALID_CATEGORY_EXCEPTION(400, "잘못된 카테고리입니다."),

    //user
    USER_NOT_FOUND_EXCEPTION(404,"사용자를 찾을 수 없습니다."),
    UNAUTHORIZED_EXCEPTION(403, "권한이 없습니다.");



    private final int status;
    private final String message;
}

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
    UNAUTHORIZED_EXCEPTION(403, "권한이 없습니다."),

    //comment
    NOT_FOUND_COMMENT(404, "댓글을 찾을 수 없습니다."),
    NOT_FOUND_PARENT_COMMENT(404, "부모댓글을 찾을 수 없습니다."),
    PARENT_COMMENT_IS_DELETED(400, "삭제된 댓글에는 대댓글을 작성할 수 없습니다."),
    CANNOT_UPDATE_DELETED_COMMENT(400,"댓글을 수정 할 수 없습니다."),

    //friend
    SELF_FRIEND_REQUEST_NOT_ALLOWED(403 , "자기 자신에게 친구 요청을 보낼 수 없습니다."),
    ALREADY_FRIENDS(400, "이미 친구인 상태입니다."),
    FRIEND_REQUEST_ALREADY_EXISTS(400, "이미 친구 요청이 존재합니다."),
    FRIEND_NOT_FOUND(404, "친구를 찾을 수 없습니다."),
    INVALID_STATUS(400, "잘못된 요청입니다."),
    SELF_FRIEND_DELETE(400,"자기 자신은 삭제 할 수 없습니다."),
    NOT_FRIEND(400,"친구가 아닙니다."),

    //ai
    QUIZ_NOT_FOUND(404, "해당 문제를 찾을 수 없습니다."),

    QUIZ_ALREADY_COMPLETED(403, "해당 난이도의 모든 스테이지를 완료했습니다."),
    QUIZ_DATA_ERROR(400, "문제 데이터 형식이 올바르지 않습니다."),

    // Auth & User
    ALREADY_EXIST_EMAIL(409, "이미 가입된 이메일입니다."),
    ALREADY_EXIST_NICKNAME(409, "이미 사용 중인 닉네임입니다."),
    PASSWORD_NOT_MATCH(401, "비밀번호가 일치하지 않습니다."),
    INVALID_VERIFICATION_CODE(400, "인증번호가 잘못되었거나 만료되었습니다."),
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    NOT_FOUND_NICKNAME(404,"해당 닉네임의 유저가 없습니다"),


    // Global
    AUTHENTICATION_NUMBER_MISMATCH(404, "인증번호 불일치"),
    MAIL_SEND_FAILED(500, "이메일 발송 중 오류가 발생했습니다."),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류가 발생했습니다.");




    private final int status;
    private final String message;
}

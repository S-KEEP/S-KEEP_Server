package Skeep.backend.friend.exception;

import Skeep.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FriendErrorCode implements ErrorCode {
    FRIEND_NOT_FOUND_WITH_TOKEN(HttpStatus.NOT_FOUND, "FRIEND_001", "발급되지 않았거나 만료된 토큰입니다."),
    FRIEND_WITH_THEMSELVES(HttpStatus.BAD_REQUEST, "FRIEND_002", "자신과는 친구를 추가할 수 없습니다."),
    ALREADY_CONNECTED(HttpStatus.BAD_REQUEST, "FRIEND_003", "이미 친구 추가가 되어있습니다."),
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, "FRIEND_004", "토큰이 만료되었습니다.(이미 친구 추가를 누군가 했습니다.)"),
    INVALID_PAGE_FRIEND(HttpStatus.BAD_REQUEST, "FRIEND_005", "유효하지 않는 페이지 넘버입니다."),
    FRIEND_NOT_FOUND(HttpStatus.BAD_REQUEST, "FRIEND_006", "대상과 친구가 아닙니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}

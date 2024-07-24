package Skeep.backend.user.exception;

import Skeep.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "USER_001", "존재하지 않는 사용자입니다.")
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}

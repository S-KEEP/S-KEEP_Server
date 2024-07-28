package Skeep.backend.auth.exception;

import Skeep.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OAuthErrorCode implements ErrorCode {
    CANNOT_JSON_PROCESS(HttpStatus.BAD_REQUEST, "OAUTH_001", "JSON을 직렬화 혹은 역직렬화할 수 없습니다."),
    NOT_MATCHED_PUBLIC_KEY(HttpStatus.UNAUTHORIZED, "OAUTH_002", "적절한 공개키를 찾을 수 없습니다."),
    NOT_FOUND_ALGORITHM(HttpStatus.INTERNAL_SERVER_ERROR, "OAUTH_003", "지원하지 않는 공개키 알고리즘입니다."),
    NOT_SUPPORTED_ALGORITHM(HttpStatus.BAD_REQUEST, "OAUTH_004", "Spring Security에서 지원하지 않는 공개키 알고리즘입니다.")
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}

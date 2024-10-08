package Skeep.backend.category.exception;

import Skeep.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CategoryErrorCode implements ErrorCode {
    INVALID_ENUM_CATEGORY(HttpStatus.BAD_REQUEST, "CATEGORY_001", "유효하지 않은 카테고리 픽스입니다."),
    USER_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CATEGORY_002", "유저 카테고리를 찾을 수 없습니다.")
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}

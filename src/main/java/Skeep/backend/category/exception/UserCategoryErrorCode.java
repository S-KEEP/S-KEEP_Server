package Skeep.backend.category.exception;

import Skeep.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserCategoryErrorCode implements ErrorCode {
    USER_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_CATEGORY_001", "유저 카테고리를 찾을 수 없습니다."),
    USER_CATEGORY_NOT_MATCHED(HttpStatus.NOT_FOUND, "USER_CATEGORY_002", "해당 유저의 카테고리가 아닙니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}

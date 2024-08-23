package Skeep.backend.location.userLocation.exception;

import Skeep.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserLocationErrorCode implements ErrorCode {
    NOT_FOUND_USER_LOCATION(HttpStatus.NOT_FOUND, "USER_LOCATION_001", "사용자의 명소 정보를 찾지 못했습니다."),
    MISMATCH_USER_AND_USER_LOCATION(HttpStatus.NOT_FOUND, "USER_LOCATION_002", "사용자와 사용자의 명소 정보가 일치하지 않습니다."),
    INVALID_PAGE_USER_LOCATION(HttpStatus.BAD_REQUEST, "USER_LOCATION_003", "유효하지 않은 페이지 넘버입니다."),
    MISMATCH_USER_LOCATION_AND_REQUEST_IMAGE_URL(HttpStatus.BAD_REQUEST, "USER_LOCATION_004", "요청한 명소와 파일명이 일치하지 않습니다."),
    BAD_REQUEST_IMAGE_URL(HttpStatus.BAD_REQUEST, "USER_LOCATION_004", "요청한 명소와 파일명이 일치하지 않습니다."),
    INVALID_CATEGORY(HttpStatus.INTERNAL_SERVER_ERROR, "USER_LOCATION_005", "알 수 없는 카테고리 오류가 발생했습니다. 발생 시각을 백엔드 개발자에게 알려주세요.")
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}

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
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}

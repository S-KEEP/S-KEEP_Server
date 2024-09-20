package Skeep.backend.fcm.exception;

import Skeep.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FcmErrorCode implements ErrorCode {
    FCM_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "FCM_001", "FCM 전송을 실패하였습니다."),
    FCM_DATA_WRITE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "FCM_002", "FCM data를 작성에 실패하였습니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}

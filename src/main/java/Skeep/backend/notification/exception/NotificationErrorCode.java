package Skeep.backend.notification.exception;

import Skeep.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NotificationErrorCode implements ErrorCode {
    INVALID_NOTIFICATION_TYPE(HttpStatus.INTERNAL_SERVER_ERROR, "NOTIFICATION_001", "알 수 없는 Notification type입니다."),
    INVALID_PAGE_NOTIFICATION(HttpStatus.BAD_REQUEST, "NOTIFICATION_002", "유효하지 않은 페이지 넘버입니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}

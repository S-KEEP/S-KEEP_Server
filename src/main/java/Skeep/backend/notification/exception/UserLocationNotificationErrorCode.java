package Skeep.backend.notification.exception;

import Skeep.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserLocationNotificationErrorCode implements ErrorCode {
    USER_LOCATION_NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_LOCATION_NOTIFICATION_001", "명소 알림을 찾을 수 없습니다.")
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}

package Skeep.backend.screenshot.exception;

import Skeep.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ScreenshotErrorCode implements ErrorCode {
    FILE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "SCREENSHOT_001", "이미지 파일이 없거나 5장 초과입니다."),
    FAILED_ANALYZE_PROCESS(HttpStatus.INTERNAL_SERVER_ERROR, "SCREENSHOT_002", "이미지 처리 중 오류가 발생했습니다."),
    PATCH_BAD_REQUEST(HttpStatus.BAD_REQUEST, "SCREENSHOT_003", "이미지 URL이 없거나 5장 초과입니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}

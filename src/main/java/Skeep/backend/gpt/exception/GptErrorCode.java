package Skeep.backend.gpt.exception;

import Skeep.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GptErrorCode implements ErrorCode {

    FAILED_GPT_API_REQUEST(HttpStatus.BAD_GATEWAY, "GPT_001", "GPT API 요청을 실패했습니다."),
    FAILED_ANALYZE_CATEGORY(HttpStatus.BAD_GATEWAY, "GPT_002", "GPT API에서 카테고리 분석을 실패했습니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}

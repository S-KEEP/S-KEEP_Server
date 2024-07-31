package Skeep.backend.naverOcr.exception;

import Skeep.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NaverOcrErrorCode implements ErrorCode {

    UNSUPPORTED_FILE_FORMAT(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "OCR_001", "지원하지 않는 파일 형식입니다."),
    UNDETERMINED_FILE(HttpStatus.BAD_REQUEST, "OCR_002", "식별할 수 없는 파일입니다."),
    FAILED_OCR_API_REQUEST(HttpStatus.BAD_GATEWAY, "OCR_003", "Naver OCR API 요청을 실패했습니다."),
    FAILED_PARSING_OCR_API_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, "OCR_004", "Naver OCR API 응답을 처리하는 중 오류가 발생했습니다."),
    FAILED_OCR_IN_API(HttpStatus.BAD_GATEWAY, "OCR_005", "Naver OCR API에서 이미지 인식을 실패했습니다."),
    BAD_IMAGE_FILE(HttpStatus.BAD_REQUEST, "OCR_006", "파일에 문제가 있습니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}

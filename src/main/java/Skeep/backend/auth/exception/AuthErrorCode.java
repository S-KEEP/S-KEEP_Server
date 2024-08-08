package Skeep.backend.auth.exception;

import Skeep.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    CANNOT_JSON_PROCESS(HttpStatus.BAD_REQUEST, "AUTH_001", "JSON을 직렬화 혹은 역직렬화할 수 없습니다."),
    NOT_MATCHED_PUBLIC_KEY(HttpStatus.UNAUTHORIZED, "AUTH_002", "적절한 공개키를 찾을 수 없습니다."),
    NOT_FOUND_ALGORITHM(HttpStatus.INTERNAL_SERVER_ERROR, "AUTH_003", "지원하지 않는 공개키 알고리즘입니다."),
    NOT_SUPPORTED_ALGORITHM(HttpStatus.BAD_REQUEST, "AUTH_004", "Spring Security에서 지원하지 않는 공개키 알고리즘입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_005", "Refresh Token이 유효하지 않습니다."),
    FAILED_TO_READ_PRIVATE_KEY(HttpStatus.INTERNAL_SERVER_ERROR, "AUTH_006", "개인 키 정보를 읽는 데 실패했습니다."),
    PEM_PARSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AUTH_007", "PEM 데이터 처리 중 오류가 발생했습니다."),
    FILE_READ_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AUTH_008", "개인 키 파일을 읽는 중 오류가 발생했습니다"),
    APPLE_REVOKE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AUTH_009", "Apple 사용자 로그아웃에 실패했습니다."),
    MISSING_TOKEN(HttpStatus.NOT_FOUND, "AUTH_010", "토큰이 비어있습니다.")
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}

package Skeep.backend.kakaoMap.exception;

import Skeep.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum KakaoMapErrorCode implements ErrorCode {

    FAILED_KAKAO_API_REQUEST(HttpStatus.BAD_GATEWAY, "KAKAO_001", "KAKAO API 요청을 실패했습니다."),
    FAILED_PARSING_KAKAO_API_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, "KAKAO_002", "KAKAO API 응답을 처리하는 중 오류가 발생했습니다."),
    FAILED_FIND_LOCATION_API(HttpStatus.BAD_GATEWAY, "KAKAO_003", "KAKAO API에서 장소 검색에 실패했습니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}


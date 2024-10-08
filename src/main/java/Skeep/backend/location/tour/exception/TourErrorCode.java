package Skeep.backend.location.tour.exception;

import Skeep.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TourErrorCode implements ErrorCode {
    CANNOT_CONVERT_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, "TOUR_001", "응답을 처리하는 중 오류가 발생했습니다."),
    CANNOT_MATCH_KAKAO_MAP(HttpStatus.INTERNAL_SERVER_ERROR, "TOUR_002", "TourAPI의 명소를 Kakao Map에서 찾을 수 없습니다.")
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}

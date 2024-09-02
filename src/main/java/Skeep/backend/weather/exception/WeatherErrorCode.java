package Skeep.backend.weather.exception;

import Skeep.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum WeatherErrorCode implements ErrorCode {
    CANNOT_CONVERT_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, "WEATHER_001", "응답을 처리하는 중 오류가 발생했습니다."),
    CANNOT_MATCH_ROAD_CODE(HttpStatus.INTERNAL_SERVER_ERROR, "WEATHER_002", "Location의 주소가 예보구역코드와 매칭되지 않습니다."),
    NOT_FOUND_WEATHER_FROM_RESPONSE(HttpStatus.NOT_FOUND, "WEATHER_003", "기상청 API의 Response에서 해당 날짜의 날씨를 찾을 수 없습니다."),
    CANNOT_CONVERT_GRID(HttpStatus.INTERNAL_SERVER_ERROR, "WEATHER_004", "GPS 좌표를 X, Y 좌표로 변환하는데 에러가 발생했습니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}

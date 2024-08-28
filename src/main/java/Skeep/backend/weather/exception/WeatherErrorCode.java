package Skeep.backend.weather.exception;

import Skeep.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum WeatherErrorCode implements ErrorCode {
    CANNOT_CONVERT_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, "WEATHER_001", "응답을 처리하는 중 오류가 발생했습니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}

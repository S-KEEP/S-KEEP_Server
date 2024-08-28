package Skeep.backend.weather.service;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.weather.dto.response.MiddleTermLandForecastResponse;
import Skeep.backend.weather.dto.response.MiddleTermTaResponse;
import Skeep.backend.weather.dto.response.ShortTermResponse;
import Skeep.backend.weather.exception.WeatherErrorCode;
import Skeep.backend.weather.util.RegionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WeatherService {
    private final ShortTermFeignClientService shortTermFeignClientService;
    private final MediumTermFeignClientService mediumTermFeignClientService;

    @Value("${weather.service-key.decoding}")
    private String serviceKey;

    public ShortTermResponse.Response.Body.Items getShortTermForecast(String x, String y) {
        String response = shortTermFeignClientService.getVillageForecast(
                serviceKey, 1000, 1, "JSON",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), "0500", x, y);

        ShortTermResponse shorTermResponse = deserializeShortTermResponse(response);
        return shorTermResponse.response().body().items();
    }

    private ShortTermResponse deserializeShortTermResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        ShortTermResponse shorTermResponse = null;
        try {
            shorTermResponse = objectMapper.readValue(response, ShortTermResponse.class);
        } catch (JsonProcessingException e) {
            throw BaseException.type(WeatherErrorCode.CANNOT_CONVERT_RESPONSE);
        }
        return shorTermResponse;
    }

    public MiddleTermLandForecastResponse.Response.Body.Items getMiddleTermLandForecast(String regionCode) {
        String response = mediumTermFeignClientService.getMidLandForecast(
                serviceKey, 10, 1, "JSON",
                regionCode,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "0600"
        );

        MiddleTermLandForecastResponse middleTermLandForecastResponse = deserializeMiddleTermLandForecastResponse(response);
        return middleTermLandForecastResponse.response().body().items();
    }

    private MiddleTermLandForecastResponse deserializeMiddleTermLandForecastResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        MiddleTermLandForecastResponse middleTermLandForecastResponse = null;
        try {
            middleTermLandForecastResponse = objectMapper.readValue(response, MiddleTermLandForecastResponse.class);
        } catch (JsonProcessingException e) {
            throw BaseException.type(WeatherErrorCode.CANNOT_CONVERT_RESPONSE);
        }
        return middleTermLandForecastResponse;
    }

    public MiddleTermTaResponse.Response.Body.Items getMiddleTermTa(String regionCode) {
        String response = mediumTermFeignClientService.getMidTaForecast(
                serviceKey, 10, 1, "JSON",
                regionCode,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "0600"
        );

        MiddleTermTaResponse middleTermTaResponse = deserializeMiddleTermTaResponse(response);
        return middleTermTaResponse.response().body().items();
    }

    private MiddleTermTaResponse deserializeMiddleTermTaResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        MiddleTermTaResponse middleTermTaResponse = null;
        try {
            middleTermTaResponse = objectMapper.readValue(response, MiddleTermTaResponse.class);
        } catch (JsonProcessingException e) {
            throw BaseException.type(WeatherErrorCode.CANNOT_CONVERT_RESPONSE);
        }
        return middleTermTaResponse;
    }
}

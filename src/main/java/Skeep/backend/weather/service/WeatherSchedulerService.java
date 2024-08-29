package Skeep.backend.weather.service;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.location.location.domain.Location;
import Skeep.backend.location.location.domain.LocationRepository;
import Skeep.backend.weather.domain.EWeatherCondition;
import Skeep.backend.weather.domain.Weather;
import Skeep.backend.weather.domain.WeatherRepository;
import Skeep.backend.weather.domain.locationGrid.LocationGrid;
import Skeep.backend.weather.dto.response.MiddleTermLandForecastResponse;
import Skeep.backend.weather.dto.response.MiddleTermTaResponse;
import Skeep.backend.weather.dto.response.ShortTermResponse.Response.Body.Items;
import Skeep.backend.weather.exception.WeatherErrorCode;
import Skeep.backend.weather.service.locationGrid.LocationGridService;
import Skeep.backend.weather.util.RegionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static Skeep.backend.weather.dto.response.ShortTermResponse.Response.Body.Items.Item;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WeatherSchedulerService {
    private final LocationRepository locationRepository;
    private final WeatherRepository weatherRepository;
    private final WeatherService weatherService;
    private final LocationGridService locationGridService;

    private LocalDate localDate = LocalDate.now();

    // 매일 아침 9시
    @Transactional
    public void updateWeather() {
        weatherRepository.deleteAll();

        List<Location> locationList = locationRepository.findAll();
        for (int i = 0; i < locationList.size(); i++) {
            LocationGrid locationGrid = locationGridService.getLocationGridWithin3kmRadius(Double.parseDouble(locationList.get(i).getX()), Double.parseDouble(locationList.get(i).getY()));
            if (locationGrid == null) {
                continue;
            }

            analyzeShortTerm(locationList.get(i), String.valueOf(locationGrid.getGridX()), String.valueOf(locationGrid.getGridY()));
            analyzeMiddleTerm(locationList.get(i), getRegionCode_land(locationList.get(i).getRoadAddress()), getRegionCode_ta(locationList.get(i).getRoadAddress()));
        }
    }

    private String getRegionCode_land(String road_address) {
        String regionCode = RegionUtils.getRegionCode_land(road_address);
        if (regionCode == null) {
            throw BaseException.type(WeatherErrorCode.CANNOT_MATCH_ROAD_CODE);
        }
        return regionCode;
    }

    private String getRegionCode_ta(String road_address) {
        String regionCode = RegionUtils.getRegionCode_ta(road_address);
        if (regionCode == null) {
            throw BaseException.type(WeatherErrorCode.CANNOT_MATCH_ROAD_CODE);
        }
        return regionCode;
    }

    @Transactional
    public void analyzeShortTerm(Location location, String x, String y) {
        Items items = weatherService.getShortTermForecast(x, y);
        List<LocalDate> dates = IntStream.range(0, 3)
                .mapToObj(i -> localDate.plusDays(i))
                .collect(Collectors.toList());

        for (LocalDate date: dates) {
            String yyyyMMdd = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            weatherRepository.save(Weather.createWeather(location, date, getEWeatherCondition_shortTerm(items, yyyyMMdd), getTemperature_shortTerm(items, yyyyMMdd)));
        }
    }

    private EWeatherCondition getEWeatherCondition_shortTerm(Items items, String yyyyMMdd) {
        Item PTY = items.item().stream()
                .filter(item -> yyyyMMdd.equals(item.fcstDate()) && "1400".equals(item.fcstTime()) && "PTY".equals(item.category()))
                .findFirst().orElseThrow(
                        () -> BaseException.type(WeatherErrorCode.NOT_FOUND_WEATHER_FROM_RESPONSE)
                );

        EWeatherCondition weatherCondition = null;
        switch (PTY.fcstValue()) {
            case "1":
                weatherCondition = EWeatherCondition.RAIN;
                break;
            case "2":
                weatherCondition = EWeatherCondition.RAIN_AND_SNOW;
                break;
            case "3":
                weatherCondition = EWeatherCondition.SNOW;
                break;
            case "4":
                weatherCondition = EWeatherCondition.SHOWER;
                break;
            default:
                Item SKY = items.item().stream()
                        .filter(item -> yyyyMMdd.equals(item.fcstDate()) && "1400".equals(item.fcstTime()) && "SKY".equals(item.category()))
                        .findFirst().orElseThrow(
                                () -> BaseException.type(WeatherErrorCode.NOT_FOUND_WEATHER_FROM_RESPONSE)
                        );
                switch (SKY.fcstValue()) {
                    case "1":
                        weatherCondition = EWeatherCondition.CLEAR;
                        break;
                    case "3":
                        weatherCondition = EWeatherCondition.PARTLY_CLOUDY;
                        break;
                    case "4":
                        weatherCondition = EWeatherCondition.CLOUDY;
                        break;
                }
        }
        return weatherCondition;
    }

    private String getTemperature_shortTerm(Items items, String yyyyMMdd) {
        Item TMX = items.item().stream()
                .filter(item -> yyyyMMdd.equals(item.fcstDate()) && "TMX".equals(item.category()))
                .findFirst().orElseThrow(
                        () -> BaseException.type(WeatherErrorCode.NOT_FOUND_WEATHER_FROM_RESPONSE)
                );
        return TMX.fcstValue();
    }

    @Transactional
    public void analyzeMiddleTerm(Location location, String regionCode_land, String regionCode_ta) {
        MiddleTermLandForecastResponse.Response.Body.Items.Item item_landForecast = weatherService.getMiddleTermLandForecast(regionCode_land).item().get(0);
        MiddleTermTaResponse.Response.Body.Items.Item item_ta = weatherService.getMiddleTermTa(regionCode_ta).item().get(0);

        List<EWeatherCondition> eWeatherConditions = List.of(
                        item_landForecast.wf3Pm(),
                        item_landForecast.wf4Pm(),
                        item_landForecast.wf5Pm(),
                        item_landForecast.wf6Pm(),
                        item_landForecast.wf7Pm(),
                        item_landForecast.wf8(),
                        item_landForecast.wf9(),
                        item_landForecast.wf10()
                ).stream()
                .map(EWeatherCondition::convertMiddle)
                .collect(Collectors.toList());

        List<String> temperatures = new ArrayList<>();
        temperatures.add(String.valueOf(item_ta.taMax3() * 0.7 + item_ta.taMin3() * 0.3));
        temperatures.add(String.valueOf(item_ta.taMax4() * 0.7 + item_ta.taMin4() * 0.3));
        temperatures.add(String.valueOf(item_ta.taMax5() * 0.7 + item_ta.taMin5() * 0.3));
        temperatures.add(String.valueOf(item_ta.taMax6() * 0.7 + item_ta.taMin6() * 0.3));
        temperatures.add(String.valueOf(item_ta.taMax7() * 0.7 + item_ta.taMin7() * 0.3));
        temperatures.add(String.valueOf(item_ta.taMax8() * 0.7 + item_ta.taMin8() * 0.3));
        temperatures.add(String.valueOf(item_ta.taMax9() * 0.7 + item_ta.taMin9() * 0.3));
        temperatures.add(String.valueOf(item_ta.taMax10() * 0.7 + item_ta.taMin10() * 0.3));

        List<LocalDate> dates = IntStream.range(3, 11)
                .mapToObj(i -> localDate.plusDays(i))
                .collect(Collectors.toList());

        for (int i = 0; i < dates.size(); i++) {
            weatherRepository.save(Weather.createWeather(location, dates.get(i), eWeatherConditions.get(i), temperatures.get(i)));
        }
    }
}

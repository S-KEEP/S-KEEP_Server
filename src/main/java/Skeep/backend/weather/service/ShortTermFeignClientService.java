package Skeep.backend.weather.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "villageForecastClient", url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0")
public interface ShortTermFeignClientService {
    @GetMapping("/getVilageFcst")
    String getVillageForecast(
            @RequestParam("serviceKey") String serviceKey,
            @RequestParam("numOfRows") int numOfRows,
            @RequestParam("pageNo") int pageNo,
            @RequestParam("dataType") String dataType,
            @RequestParam("base_date") String baseDate,
            @RequestParam("base_time") String baseTime,
            @RequestParam("nx") String nx,
            @RequestParam("ny") String ny
    );
}
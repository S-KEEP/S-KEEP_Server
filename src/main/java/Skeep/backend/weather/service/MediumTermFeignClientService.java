package Skeep.backend.weather.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "midForecastClient", url = "http://apis.data.go.kr/1360000/MidFcstInfoService")
public interface MediumTermFeignClientService {
    @GetMapping("/getMidLandFcst")
    String getMidLandForecast(
            @RequestParam("serviceKey") String serviceKey,
            @RequestParam("numOfRows") int numOfRows,
            @RequestParam("pageNo") int pageNo,
            @RequestParam("dataType") String dataType,
            @RequestParam("regId") String regId,
            @RequestParam("tmFc") String tmFc
    );

    @GetMapping("/getMidTa")
    String getMidTaForecast(
            @RequestParam("serviceKey") String serviceKey,
            @RequestParam("numOfRows") int numOfRows,
            @RequestParam("pageNo") int pageNo,
            @RequestParam("dataType") String dataType,
            @RequestParam("regId") String regId,
            @RequestParam("tmFc") String tmFc
    );
}
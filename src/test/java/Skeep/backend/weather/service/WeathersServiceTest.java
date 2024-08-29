package Skeep.backend.weather.service;

import Skeep.backend.global.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class WeathersServiceTest extends ServiceTest {
    @Autowired
    private WeatherService weatherService;

    @Test
    public void getShortTermForecast() {
//        System.out.println(weatherService.getShortTermForecast("127", "37"));
    }

    @Test
    public void getMiddleTermLandForecast() {
//        System.out.println(weatherService.getMiddleTermLandForecast("11B00000"));
    }

    @Test
    public void getMiddleTermTa(){
//        System.out.println(weatherService.getMiddleTermTa("11B00000"));
    }
}
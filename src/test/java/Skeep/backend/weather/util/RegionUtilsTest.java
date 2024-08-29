package Skeep.backend.weather.util;

import org.junit.jupiter.api.Test;

class RegionUtilsTest {
    @Test
    void getRegionCode() {
        System.out.println(RegionUtils.getRegionCode_land("인천 중구 은하수로 10"));
    }
}
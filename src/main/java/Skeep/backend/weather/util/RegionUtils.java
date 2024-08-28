package Skeep.backend.weather.util;

import java.util.HashMap;
import java.util.Map;

public class RegionUtils {
    private static final Map<String, double[]> regionCoordinates = new HashMap<>();

    static {
        regionCoordinates.put("11B00000", new double[]{37.5665, 126.9780}); // 서울, 인천, 경기도
        regionCoordinates.put("11D10000", new double[]{37.5550, 128.2094}); // 강원도영서
        regionCoordinates.put("11D20000", new double[]{37.8228, 128.1555}); // 강원도영동
        regionCoordinates.put("11C20000", new double[]{36.3214, 127.4197}); // 대전, 세종, 충청남도
        regionCoordinates.put("11C10000", new double[]{36.6353, 127.4914}); // 충청북도
        regionCoordinates.put("11F20000", new double[]{35.1595, 126.8526}); // 광주, 전라남도
        regionCoordinates.put("11F10000", new double[]{35.8204, 127.1087}); // 전북자치도
        regionCoordinates.put("11H10000", new double[]{35.8714, 128.6014}); // 대구, 경상북도
        regionCoordinates.put("11H20000", new double[]{35.1796, 129.0756}); // 부산, 울산, 경상남도
        regionCoordinates.put("11G00000", new double[]{33.4996, 126.5312}); // 제주도
    }

    public static String getRegionCode(double latitude, double longitude) {
        String nearestRegion = null;
        double minDistance = Double.MAX_VALUE;

        for (Map.Entry<String, double[]> entry : regionCoordinates.entrySet()) {
            double[] coords = entry.getValue();
            double distance = calculateDistance(latitude, longitude, coords[0], coords[1]);

            if (distance < minDistance) {
                minDistance = distance;
                nearestRegion = entry.getKey();
            }
        }

        return nearestRegion;
    }

    private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
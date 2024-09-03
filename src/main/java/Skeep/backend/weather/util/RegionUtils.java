package Skeep.backend.weather.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RegionUtils {
    private static final Map<String, List<String>> regionCoordinates_land = new HashMap<>();
    static {
        regionCoordinates_land.put("11B00000", List.of("서울", "인천", "경기")); // 서울, 인천, 경기도
        regionCoordinates_land.put("11D10000", List.of("춘천", "원주", "홍천", "횡성", "영월", "평창", "정선", "철원", "화천", "양구", "인제", "이천", "평강", "김화", "회양")); // 강원도영서
        regionCoordinates_land.put("11D20000", List.of("강릉", "삼척", "동해", "태백", "속초", "양양", "고성", "통천")); // 강원도영동
        regionCoordinates_land.put("11C20000", List.of("대전", "세종", "충남")); // 대전, 세종, 충청남도
        regionCoordinates_land.put("11C10000", List.of("충북")); // 충청북도
        regionCoordinates_land.put("11F20000", List.of("광주", "전남")); // 광주, 전라남도
        regionCoordinates_land.put("11F10000", List.of("전북특별자치도")); // 전북자치도
        regionCoordinates_land.put("11H10000", List.of("대구", "경북")); // 대구, 경상북도
        regionCoordinates_land.put("11H20000", List.of("부산", "울산", "경남")); // 부산, 울산, 경상남도
        regionCoordinates_land.put("11G00000", List.of("제주특별자치도")); // 제주도
    }

    private static final Map<String, List<String>> regionCoordinates_ta = new HashMap<>();
    static {
        regionCoordinates_ta.put("11A00101", List.of("백령도"));
        regionCoordinates_ta.put("11B10101", List.of("서울"));
        regionCoordinates_ta.put("11B10102", List.of("과천"));
        regionCoordinates_ta.put("11B10103", List.of("광명"));
        regionCoordinates_ta.put("11B20101", List.of("강화"));
        regionCoordinates_ta.put("11B20102", List.of("김포"));
        regionCoordinates_ta.put("11B20201", List.of("인천"));
        regionCoordinates_ta.put("11B20202", List.of("시흥"));
        regionCoordinates_ta.put("11B20203", List.of("안산"));
        regionCoordinates_ta.put("11B20204", List.of("부천"));
        regionCoordinates_ta.put("11B20301", List.of("의정부"));
        regionCoordinates_ta.put("11B20302", List.of("고양"));
        regionCoordinates_ta.put("11B20304", List.of("양주"));
        regionCoordinates_ta.put("11B20305", List.of("파주"));
        regionCoordinates_ta.put("11B20401", List.of("동두천"));
        regionCoordinates_ta.put("11B20402", List.of("연천"));
        regionCoordinates_ta.put("11B20403", List.of("포천"));
        regionCoordinates_ta.put("11B20404", List.of("가평"));
        regionCoordinates_ta.put("11B20501", List.of("구리"));
        regionCoordinates_ta.put("11B20502", List.of("남양주"));
        regionCoordinates_ta.put("11B20503", List.of("양평"));
        regionCoordinates_ta.put("11B20504", List.of("하남"));
        regionCoordinates_ta.put("11B20601", List.of("수원"));
        regionCoordinates_ta.put("11B20602", List.of("안양"));
        regionCoordinates_ta.put("11B20603", List.of("오산"));
        regionCoordinates_ta.put("11B20604", List.of("화성"));
        regionCoordinates_ta.put("11B20605", List.of("성남"));
        regionCoordinates_ta.put("11B20606", List.of("평택"));
        regionCoordinates_ta.put("11B20609", List.of("의왕"));
        regionCoordinates_ta.put("11B20610", List.of("군포"));
        regionCoordinates_ta.put("11B20611", List.of("안성"));
        regionCoordinates_ta.put("11B20612", List.of("용인"));
        regionCoordinates_ta.put("11B20701", List.of("이천"));
        regionCoordinates_ta.put("11B20702", List.of("광주"));
        regionCoordinates_ta.put("11B20703", List.of("여주"));
        regionCoordinates_ta.put("11C10101", List.of("충주"));
        regionCoordinates_ta.put("11C10102", List.of("진천"));
        regionCoordinates_ta.put("11C10103", List.of("음성"));
        regionCoordinates_ta.put("11C10201", List.of("제천"));
        regionCoordinates_ta.put("11C10202", List.of("단양"));
        regionCoordinates_ta.put("11C10301", List.of("청주"));
        regionCoordinates_ta.put("11C10302", List.of("보은"));
        regionCoordinates_ta.put("11C10303", List.of("괴산"));
        regionCoordinates_ta.put("11C10304", List.of("증평"));
        regionCoordinates_ta.put("11C10401", List.of("추풍령"));
        regionCoordinates_ta.put("11C10402", List.of("영동"));
        regionCoordinates_ta.put("11C10403", List.of("옥천"));
        regionCoordinates_ta.put("11C20101", List.of("서산"));
        regionCoordinates_ta.put("11C20102", List.of("태안"));
        regionCoordinates_ta.put("11C20103", List.of("당진"));
        regionCoordinates_ta.put("11C20104", List.of("홍성"));
        regionCoordinates_ta.put("11C20201", List.of("보령"));
        regionCoordinates_ta.put("11C20202", List.of("서천"));
        regionCoordinates_ta.put("11C20301", List.of("천안"));
        regionCoordinates_ta.put("11C20302", List.of("아산"));
        regionCoordinates_ta.put("11C20303", List.of("예산"));
        regionCoordinates_ta.put("11C20401", List.of("대전"));
        regionCoordinates_ta.put("11C20402", List.of("공주"));
        regionCoordinates_ta.put("11C20403", List.of("계룡"));
        regionCoordinates_ta.put("11C20404", List.of("세종"));
        regionCoordinates_ta.put("11C20501", List.of("부여"));
        regionCoordinates_ta.put("11C20502", List.of("청양"));
        regionCoordinates_ta.put("11C20601", List.of("금산"));
        regionCoordinates_ta.put("11C20602", List.of("논산"));
        regionCoordinates_ta.put("11D10101", List.of("철원"));
        regionCoordinates_ta.put("11D10102", List.of("화천"));
        regionCoordinates_ta.put("11D10201", List.of("인제"));
        regionCoordinates_ta.put("11D10202", List.of("양구"));
        regionCoordinates_ta.put("11D10301", List.of("춘천"));
        regionCoordinates_ta.put("11D10302", List.of("홍천"));
        regionCoordinates_ta.put("11D10401", List.of("원주"));
        regionCoordinates_ta.put("11D10402", List.of("횡성"));
        regionCoordinates_ta.put("11D10501", List.of("영월"));
        regionCoordinates_ta.put("11D10502", List.of("정선"));
        regionCoordinates_ta.put("11D10503", List.of("평창"));
        regionCoordinates_ta.put("11D20201", List.of("대관령"));
        regionCoordinates_ta.put("11D20301", List.of("태백"));
        regionCoordinates_ta.put("11D20401", List.of("속초"));
        regionCoordinates_ta.put("11D20402", List.of("고성"));
        regionCoordinates_ta.put("11D20403", List.of("양양"));
        regionCoordinates_ta.put("11D20501", List.of("강릉"));
        regionCoordinates_ta.put("11D20601", List.of("동해"));
        regionCoordinates_ta.put("11D20602", List.of("삼척"));
        regionCoordinates_ta.put("11E00101", List.of("울릉도"));
        regionCoordinates_ta.put("11E00102", List.of("독도"));
        regionCoordinates_ta.put("11F10201", List.of("전주"));
        regionCoordinates_ta.put("11F10202", List.of("익산"));
        regionCoordinates_ta.put("11F10203", List.of("정읍"));
        regionCoordinates_ta.put("11F10204", List.of("완주"));
        regionCoordinates_ta.put("11F10301", List.of("장수"));
        regionCoordinates_ta.put("11F10303", List.of("진안"));
        regionCoordinates_ta.put("11F10401", List.of("남원"));
        regionCoordinates_ta.put("11F10402", List.of("임실"));
        regionCoordinates_ta.put("11F10403", List.of("순창"));
        regionCoordinates_ta.put("21F10501", List.of("군산"));
        regionCoordinates_ta.put("21F10502", List.of("김제"));
        regionCoordinates_ta.put("21F10601", List.of("고창"));
        regionCoordinates_ta.put("21F10602", List.of("부안"));
        regionCoordinates_ta.put("21F20101", List.of("함평"));
        regionCoordinates_ta.put("21F20102", List.of("영광"));
        regionCoordinates_ta.put("21F20201", List.of("진도"));
        regionCoordinates_ta.put("11F20301", List.of("완도"));
        regionCoordinates_ta.put("11F20302", List.of("해남"));
        regionCoordinates_ta.put("11F20303", List.of("강진"));
        regionCoordinates_ta.put("11F20304", List.of("장흥"));
        regionCoordinates_ta.put("11F20401", List.of("여수"));
        regionCoordinates_ta.put("11F20402", List.of("광양"));
        regionCoordinates_ta.put("11F20403", List.of("고흥"));
        regionCoordinates_ta.put("11F20404", List.of("보성"));
        regionCoordinates_ta.put("11F20405", List.of("순천시"));
        regionCoordinates_ta.put("11F20501", List.of("광주"));
        regionCoordinates_ta.put("11F20502", List.of("장성"));
        regionCoordinates_ta.put("11F20503", List.of("나주"));
        regionCoordinates_ta.put("11F20504", List.of("담양"));
        regionCoordinates_ta.put("11F20505", List.of("화순"));
        regionCoordinates_ta.put("11F20601", List.of("구례"));
        regionCoordinates_ta.put("11F20602", List.of("곡성"));
        regionCoordinates_ta.put("11F20603", List.of("순천"));
        regionCoordinates_ta.put("11F20701", List.of("흑산도"));
        regionCoordinates_ta.put("21F20801", List.of("목포"));
        regionCoordinates_ta.put("21F20802", List.of("영암"));
        regionCoordinates_ta.put("21F20803", List.of("신안"));
        regionCoordinates_ta.put("21F20804", List.of("무안"));
        regionCoordinates_ta.put("11G00101", List.of("성산"));
        regionCoordinates_ta.put("11G00201", List.of("제주"));
        regionCoordinates_ta.put("11G00302", List.of("성판악"));
        regionCoordinates_ta.put("11G00401", List.of("서귀포"));
        regionCoordinates_ta.put("11G00501", List.of("고산"));
        regionCoordinates_ta.put("11G00601", List.of("이어도"));
        regionCoordinates_ta.put("11G00800", List.of("추자도"));
        regionCoordinates_ta.put("11H10101", List.of("울진"));
        regionCoordinates_ta.put("11H10102", List.of("영덕"));
        regionCoordinates_ta.put("11H10201", List.of("포항"));
        regionCoordinates_ta.put("11H10202", List.of("경주"));
        regionCoordinates_ta.put("11H10301", List.of("문경"));
        regionCoordinates_ta.put("11H10302", List.of("상주"));
        regionCoordinates_ta.put("11H10303", List.of("예천"));
        regionCoordinates_ta.put("11H10401", List.of("영주"));
        regionCoordinates_ta.put("11H10402", List.of("봉화"));
        regionCoordinates_ta.put("11H10403", List.of("영양"));
        regionCoordinates_ta.put("11H10501", List.of("안동"));
        regionCoordinates_ta.put("11H10502", List.of("의성"));
        regionCoordinates_ta.put("11H10503", List.of("청송"));
        regionCoordinates_ta.put("11H10601", List.of("김천"));
        regionCoordinates_ta.put("11H10602", List.of("구미"));
        regionCoordinates_ta.put("11H10707", List.of("군위"));
        regionCoordinates_ta.put("11H10604", List.of("고령"));
        regionCoordinates_ta.put("11H10605", List.of("성주"));
        regionCoordinates_ta.put("11H10701", List.of("대구"));
        regionCoordinates_ta.put("11H10702", List.of("영천"));
        regionCoordinates_ta.put("11H10703", List.of("경산"));
        regionCoordinates_ta.put("11H10704", List.of("청도"));
        regionCoordinates_ta.put("11H10705", List.of("칠곡"));
        regionCoordinates_ta.put("11H20101", List.of("울산"));
        regionCoordinates_ta.put("11H20102", List.of("양산"));
        regionCoordinates_ta.put("11H20201", List.of("부산"));
        regionCoordinates_ta.put("11H20301", List.of("창원"));
        regionCoordinates_ta.put("11H20304", List.of("김해"));
        regionCoordinates_ta.put("11H20401", List.of("통영"));
        regionCoordinates_ta.put("11H20402", List.of("사천"));
        regionCoordinates_ta.put("11H20403", List.of("거제"));
        regionCoordinates_ta.put("11H20404", List.of("고성"));
        regionCoordinates_ta.put("11H20405", List.of("남해"));
        regionCoordinates_ta.put("11H20501", List.of("함양"));
        regionCoordinates_ta.put("11H20502", List.of("거창"));
        regionCoordinates_ta.put("11H20503", List.of("합천"));
        regionCoordinates_ta.put("11H20601", List.of("밀양"));
        regionCoordinates_ta.put("11H20602", List.of("의령"));
        regionCoordinates_ta.put("11H20603", List.of("함안"));
        regionCoordinates_ta.put("11H20604", List.of("창녕"));
        regionCoordinates_ta.put("11H20701", List.of("진주"));
        regionCoordinates_ta.put("11H20703", List.of("산청"));
        regionCoordinates_ta.put("11H20704", List.of("하동"));
        regionCoordinates_ta.put("11I10001", List.of("사리원"));
        regionCoordinates_ta.put("11I10002", List.of("신계"));
        regionCoordinates_ta.put("11I20001", List.of("해주"));
        regionCoordinates_ta.put("11I20002", List.of("개성"));
        regionCoordinates_ta.put("11I20003", List.of("장연", "용연"));
        regionCoordinates_ta.put("11J10001", List.of("신의주"));
        regionCoordinates_ta.put("11J10002", List.of("삭주", "수풍"));
        regionCoordinates_ta.put("11J10003", List.of("구성"));
        regionCoordinates_ta.put("11J10004", List.of("자성", "중강"));
        regionCoordinates_ta.put("11J10005", List.of("강계"));
        regionCoordinates_ta.put("11J10006", List.of("희천"));
        regionCoordinates_ta.put("11J20001", List.of("평양"));
        regionCoordinates_ta.put("11J20002", List.of("진남포", "남포"));
        regionCoordinates_ta.put("11J20004", List.of("안주"));
        regionCoordinates_ta.put("11J20005", List.of("양덕"));
        regionCoordinates_ta.put("11K10001", List.of("청진"));
        regionCoordinates_ta.put("11K10002", List.of("웅기", "선봉"));
        regionCoordinates_ta.put("11K10003", List.of("성진", "김책"));
        regionCoordinates_ta.put("11K10004", List.of("무산", "삼지연"));
        regionCoordinates_ta.put("11K20001", List.of("함흥"));
        regionCoordinates_ta.put("11K20002", List.of("장진"));
        regionCoordinates_ta.put("11K20003", List.of("북청", "신포"));
        regionCoordinates_ta.put("11K20004", List.of("혜산"));
        regionCoordinates_ta.put("11K20005", List.of("풍산"));
        regionCoordinates_ta.put("11L10001", List.of("원산"));
        regionCoordinates_ta.put("11L10002", List.of("고성", "장전"));
        regionCoordinates_ta.put("11L10003", List.of("평강"));
    }

    public static String getRegionCode_land(String road_address) {
        for (Map.Entry<String, List<String>> entry : regionCoordinates_land.entrySet()) {
            for (String s : entry.getValue()) {
                if (road_address.contains(s)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    public static String getRegionCode_ta(String road_address) {
        for (Map.Entry<String, List<String>> entry : regionCoordinates_ta.entrySet()) {
            for (String s : entry.getValue()) {
                if (road_address.contains(s)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }
}
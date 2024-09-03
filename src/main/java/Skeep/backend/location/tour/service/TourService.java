package Skeep.backend.location.tour.service;

import Skeep.backend.category.domain.ECategory;
import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.service.UserCategoryRetriever;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.util.ImageConverter;
import Skeep.backend.gpt.service.GptFeignClientService;
import Skeep.backend.gpt.service.dto.ChatGptRequest;
import Skeep.backend.gpt.service.dto.ChatGptResponse;
import Skeep.backend.kakaoMap.dto.response.KakaoResponseResult;
import Skeep.backend.kakaoMap.service.KakaoMapService;
import Skeep.backend.location.location.domain.Location;
import Skeep.backend.location.location.service.LocationRetriever;
import Skeep.backend.location.location.service.LocationSaver;
import Skeep.backend.location.tour.domain.EContentType;
import Skeep.backend.location.tour.dto.TourLocationDto;
import Skeep.backend.location.tour.dto.response.TourLocationList;
import Skeep.backend.location.tour.dto.response.TourLocationResponse;
import Skeep.backend.location.tour.exception.TourErrorCode;
import Skeep.backend.location.userLocation.domain.UserLocation;
import Skeep.backend.location.userLocation.service.UserLocationSaver;
import Skeep.backend.s3.service.S3Service;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.service.UserFindService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TourService {
    private final KoreanTourInfoService koreanTourInfoService;
    private final KakaoMapService kakaoMapService;
    private final UserFindService userFindService;
    private final LocationRetriever locationRetriever;
    private final UserLocationSaver userLocationSaver;
    private final S3Service s3Service;
    private final GptFeignClientService gptFeignClientService;
    private final UserCategoryRetriever userCategoryRetriever;
    private final LocationSaver locationSaver;

    @Value("${tour.service-key.encoding}")
    private String serviceKey;

    private static int numOfRows = 3;
    private static int pageNo = 1;
    private static String mobileOS = "ETC";
    private static String mobileApp = "SKEEP";
    private static String arrange = "S";
    private static int radius = 20000;
    private static String listYN = "Y";

    public TourLocationList getLocationBasedList(double mapX, double mapY) {
        String response = koreanTourInfoService.getLocationBasedList(
                serviceKey, numOfRows, pageNo, mobileOS, mobileApp, arrange, mapX, mapY, radius, listYN, "json");
        TourLocationResponse tourLocationResponse = deserializeTourLocationResponse(response);

        if (tourLocationResponse.response().body().items() == null ||
                tourLocationResponse.response().body().items().item() == null) {
            return new TourLocationList(0, null);
        }

        List<TourLocationDto> tourLocationDtos = convertToLocationDtos(tourLocationResponse);
        return new TourLocationList(tourLocationDtos.size(), tourLocationDtos);
    }

    private TourLocationResponse deserializeTourLocationResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        TourLocationResponse tourLocationResponse = null;
        try {
            tourLocationResponse = objectMapper.readValue(response, TourLocationResponse.class);
        } catch (JsonProcessingException e) {
            throw BaseException.type(TourErrorCode.CANNOT_CONVERT_RESPONSE);
        }

        return tourLocationResponse;
    }

    private List<TourLocationDto> convertToLocationDtos(TourLocationResponse response) {
        return response.response()
                .body()
                .items()
                .item()
                .stream()
                .map(tourLocation -> new TourLocationDto(
                        tourLocation.title(),
                        tourLocation.mapx(),
                        tourLocation.mapy(),
                        String.join(" ", tourLocation.addr1()),
                        tourLocation.dist(),
                        tourLocation.contenttypeid(),
                        tourLocation.firstimage()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserLocation createUserLocationByTourAPI(Long userId, TourLocationDto tourLocationDto, Long userCategoryId) {
        User user = userFindService.findUserByIdAndStatus(userId);

        List<KakaoResponseResult> kakaoLocationIdList = kakaoMapService.getKakaoLocationIdList(List.of(tourLocationDto.title()));
        if (kakaoLocationIdList.isEmpty()) {
            throw BaseException.type(TourErrorCode.CANNOT_MATCH_KAKAO_MAP);
        }

        ChatGptResponse chatGptResponse = gptFeignClientService.sendRequest(createGptRequest_Category(tourLocationDto.title(), tourLocationDto.contentTypeId()));
        ECategory eCategory = ECategory.findByName(chatGptResponse.text());

        Location location;
        if (locationRetriever.existsByKakaoMapId(kakaoLocationIdList.get(0).id())) {
            location = locationRetriever.findByKakaoMapId(kakaoLocationIdList.get(0).id());
        } else {
            location = locationSaver.saveLocation(Location.createLocation(kakaoLocationIdList.get(0).id(), kakaoLocationIdList.get(0).placeName(), kakaoLocationIdList.get(0).roadAddress(), kakaoLocationIdList.get(0).x(), kakaoLocationIdList.get(0).y(), eCategory));
        }

        MultipartFile multipartFile = ImageConverter.convertUrlToMultipartFile(tourLocationDto.imageUrl());

        String fileName = s3Service.uploadToS3(multipartFile);

        UserCategory userCategory = userCategoryRetriever.findById(userCategoryId);
        return userLocationSaver.createUserLocation(UserLocation.createUserLocation(fileName, location, user, userCategory));
    }

    private ChatGptRequest createGptRequest_Category(String placeName, String contentTypeId) {
        String categoryNameList = Arrays.stream(ECategory.values())
                .map(ECategory::getName)
                .collect(Collectors.joining(","));

        EContentType contentType = EContentType.findById(Integer.parseInt(contentTypeId));
        String content = "제시문: 장소 이름을 줄께. 가장 일치하는 카테고리 중에서 하나를 골라서 알려줘. \n"
                + "장소 이름: " + placeName + "\n"
                + "카테고리: " + categoryNameList + "\n"
                + "힌트: " + contentType + "/n"
                + "답변: ";

        return new ChatGptRequest(content);
    }
}
package Skeep.backend.screenshot.service;

import Skeep.backend.category.domain.ECategory;
import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.domain.UserCategoryRepository;
import Skeep.backend.category.service.UserCategorySaver;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.gpt.service.GptService;
import Skeep.backend.kakaoMap.dto.response.KakaoResponseResult;
import Skeep.backend.kakaoMap.service.KakaoMapService;
import Skeep.backend.location.location.domain.Location;
import Skeep.backend.location.location.service.LocationRetriever;
import Skeep.backend.location.location.service.LocationSaver;
import Skeep.backend.location.userLocation.domain.UserLocation;
import Skeep.backend.location.userLocation.service.UserLocationSaver;
import Skeep.backend.naverOcr.service.NaverOcrService;
import Skeep.backend.s3.service.S3Service;
import Skeep.backend.screenshot.dto.request.ScreenshotUploadDto;
import Skeep.backend.screenshot.exception.ScreenshotErrorCode;
import Skeep.backend.screenshot.util.LocationAndCategory;
import Skeep.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScreenshotService {
    /**
     * 순환 참조 주의 : ScreenshotService는 다른 서비스에서 의존되어지면 안 됩니다.
     */
    private final UserLocationSaver userLocationSaver;
    private final LocationRetriever locationRetriever;
    private final LocationSaver locationSaver;
    private final UserCategorySaver userCategorySaver;
    private final NaverOcrService naverOcrService;
    private final GptService gptService;
    private final KakaoMapService kakaoMapService;
    private final S3Service s3Service;

    private final UserCategoryRepository userCategoryRepository;

    @Transactional
    public List<UserLocation> analyzeImageAndSaveResult(
            User currentUser,
            ScreenshotUploadDto screenshotUploadDto
    ) {
        List<MultipartFile> imageList = screenshotUploadDto.getFile();
        log.info("imageList: {}", imageList);
        if (imageList.isEmpty() || imageList.size() > 5)
            throw BaseException.type(ScreenshotErrorCode.FILE_BAD_REQUEST);

        // OCR API
        List<String> imageTextList = naverOcrService.getImageTextList(imageList);
        log.info("imageTextList: {}", imageTextList);

        // GPT API
        List<LocationAndCategory> locationNameAndCategoryList = gptService.getGptAnalyze(imageTextList);
        log.info("imageLocationAndCategoryList: {}", locationNameAndCategoryList);

        List<String> locationNameList = locationNameAndCategoryList
                                                    .stream()
                                                    .map(LocationAndCategory::locationName)
                                                    .toList();
        // Kakao API
        List<KakaoResponseResult> kakaoResponseResultList = kakaoMapService.getKakaoLocationIdList(locationNameList);
        log.info("kakaoResponseResultList: {}", kakaoResponseResultList);

        if (imageList.size() != imageTextList.size() || imageList.size() != kakaoResponseResultList.size())
            throw BaseException.type(ScreenshotErrorCode.FILE_BAD_REQUEST);

        return IntStream.range(0, imageList.size())
                        .mapToObj(i -> readyAndUploadToS3(
                                currentUser,
                                imageList.get(i),
                                kakaoResponseResultList.get(i),
                                locationNameAndCategoryList.get(i).category()
                            )
                        )
                        .toList();
    }

    private UserLocation readyAndUploadToS3(
            User currentUser,
            MultipartFile file,
            KakaoResponseResult kakaoResponseResult,
            ECategory category
    ) {
        UserLocation userLocation = userLocationSaver.createUserLocation(currentUser);
        String fileName = s3Service.uploadToS3(userLocation.getId(), file);
        Location location;
        if (locationRetriever.existsByKakaoMapId(kakaoResponseResult.id()))
            location = locationRetriever.findByKakaoMapId(kakaoResponseResult.id());
        else
            location = getLocation(kakaoResponseResult, category);
        UserCategory userCategory = getCategory(currentUser, location);
        userLocation.updateUserLocation(fileName, location, userCategory);

        return userLocation;
    }

    private UserCategory getCategory(
            final User currentUser,
            final Location location
    ) {
        // TODO: UserCategoryRetriever 생기면 그때 이 로직 수정할 것
        return userCategoryRepository
                .findByUserAndName(currentUser, location.getFixedCategory().getName())
                .orElseGet(() ->
                        userCategorySaver.saveUserCategory(
                            UserCategory.builder()
                                        .user(currentUser)
                                        .name(location.getFixedCategory().getName())
                                        .description(null)
                                        .build()
                        )
                );
    }

    private Location getLocation(
            final KakaoResponseResult kakaoResponseResult,
            final ECategory category
    ) {
        return locationSaver.saveLocation(
                Location.builder()
                        .kakaoMapId(kakaoResponseResult.id())
                        .x(kakaoResponseResult.x())
                        .y(kakaoResponseResult.y())
                        .fixedCategory(category)
                        .build()
        );
    }
}
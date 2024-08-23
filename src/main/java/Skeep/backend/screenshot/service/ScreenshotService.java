package Skeep.backend.screenshot.service;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.gpt.service.GptService;
import Skeep.backend.kakaoMap.dto.response.KakaoResponseResult;
import Skeep.backend.kakaoMap.service.KakaoMapService;
import Skeep.backend.location.userLocation.domain.UserLocation;
import Skeep.backend.location.userLocation.dto.request.UserLocationImagePatchDto;
import Skeep.backend.location.userLocation.service.UserLocationTransactionalService;
import Skeep.backend.naverOcr.service.NaverOcrService;
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
    private final NaverOcrService naverOcrService;
    private final GptService gptService;
    private final KakaoMapService kakaoMapService;
    private final UserLocationTransactionalService userLocationTransactionalService;

    public List<UserLocation> analyzeImageAndSaveResult(
            User currentUser,
            ScreenshotUploadDto screenshotUploadDto
    ) {
        List<MultipartFile> imageList = screenshotUploadDto.getFile();
        log.info("imageList: {}", imageList);
        if (imageList.isEmpty() || imageList.size() > 5)
            throw BaseException.type(ScreenshotErrorCode.FILE_BAD_REQUEST);

        // OCR API
        List<String> imageTextList = naverOcrService.getImageTextListWithFile(imageList);
        log.info("imageTextList: {}", imageTextList);

        List<String> presentTextList = imageTextList.stream()
                                                    .filter(imageText -> !imageText.isEmpty())
                                                    .toList();

        // GPT API
        List<LocationAndCategory> gptServiceResultList = gptService.getGptAnalyze(presentTextList);
        log.info("gptServiceResultList: {}", gptServiceResultList);

        List<LocationAndCategory> locationNameAndCategoryList
                = imageTextList.stream().map(s -> {
                    if (s.isEmpty() || gptServiceResultList.isEmpty())
                        return LocationAndCategory.of("", "");
                    else
                        return gptServiceResultList.remove(0);
                })
                .toList();
        log.info("locationAndCategoryList: {}", locationNameAndCategoryList);

        List<String> locationNameList = locationNameAndCategoryList.stream()
                                                                   .map(LocationAndCategory::locationName)
                                                                   .toList();
        List<String> presentLocationNameList =
                locationNameAndCategoryList.stream()
                                           .map(LocationAndCategory::locationName)
                                           .filter(locationName -> !locationName.isEmpty())
                                           .toList();

        List<KakaoResponseResult> kakaoMapServiceResultList
                = kakaoMapService.getKakaoLocationIdList(presentLocationNameList);
        log.info("kakaoMapServiceResultList: {}", kakaoMapServiceResultList);

        List<KakaoResponseResult> kakaoResponseResultList
                = locationNameList.stream() .map(s -> {
                    if (s.isEmpty() || kakaoMapServiceResultList.isEmpty())
                        return KakaoResponseResult.of("", "", "", "", "");
                    return kakaoMapServiceResultList.remove(0);
                })
                .toList();

        if (imageList.size() != imageTextList.size() || imageList.size() != kakaoResponseResultList.size())
            throw BaseException.type(ScreenshotErrorCode.FILE_BAD_REQUEST);

        log.info("LocationAndCategoryList: {}", locationNameAndCategoryList);
        return IntStream.range(0, kakaoResponseResultList.size())
                .filter(i -> !kakaoResponseResultList.get(i).id().isEmpty())
                .mapToObj(i -> userLocationTransactionalService.readyAndUploadToS3(
                        currentUser,
                        imageList.get(i),
                        kakaoResponseResultList.get(i),
                        locationNameAndCategoryList.get(i).category()
                    )
                )
                .toList();
    }

    @Transactional
    public List<UserLocation> reanalyzeImageAndSaveResult(
            User currentUser,
            List<UserLocation> userLocationList,
            List<UserLocationImagePatchDto> userLocationImagePatchDtoList
    ) {
        if (userLocationImagePatchDtoList.isEmpty() || userLocationImagePatchDtoList.size() > 5)
            throw BaseException.type(ScreenshotErrorCode.PATCH_BAD_REQUEST);

        List<String> imageUrlList = userLocationImagePatchDtoList.stream()
                                                                 .map(UserLocationImagePatchDto::imageUrl)
                                                                 .toList();

        // OCR API
        List<String> imageTextList = naverOcrService.getImageTextListWithUrl(imageUrlList);
        log.info("imageTextList: {}", imageTextList);

        List<String> presentTextList = imageTextList.stream()
                .filter(imageText -> !imageText.isEmpty())
                .toList();

        // GPT API
        List<LocationAndCategory> gptServiceResultList = gptService.getGptAnalyze(presentTextList);
        log.info("gptServiceResultList: {}", gptServiceResultList);

        List<LocationAndCategory> locationNameAndCategoryList
                = imageTextList.stream().map(s -> {
                    if (s.isEmpty() || gptServiceResultList.isEmpty())
                        return LocationAndCategory.of("", "");
                    else
                        return gptServiceResultList.remove(0);
                })
                .toList();
        log.info("locationAndCategoryList: {}", locationNameAndCategoryList);

        List<String> locationNameList = locationNameAndCategoryList.stream()
                .map(LocationAndCategory::locationName)
                .toList();
        List<String> presentLocationNameList =
                locationNameAndCategoryList.stream()
                        .map(LocationAndCategory::locationName)
                        .filter(locationName -> !locationName.isEmpty())
                        .toList();

        List<KakaoResponseResult> kakaoMapServiceResultList
                = kakaoMapService.getKakaoLocationIdList(presentLocationNameList);
        log.info("kakaoMapServiceResultList: {}", kakaoMapServiceResultList);

        List<KakaoResponseResult> kakaoResponseResultList
                = locationNameList.stream() .map(s -> {
                    if (s.isEmpty() || kakaoMapServiceResultList.isEmpty())
                        return KakaoResponseResult.of("", "", "", "", "");
                    return kakaoMapServiceResultList.remove(0);
                })
                .toList();

        if (imageUrlList.size() != imageTextList.size() ||imageUrlList.size() != kakaoResponseResultList.size())
            throw BaseException.type(ScreenshotErrorCode.FILE_BAD_REQUEST);

        log.info("LocationAndCategoryList: {}", locationNameAndCategoryList);
        return IntStream.range(0, kakaoResponseResultList.size())
                .filter(i -> !kakaoResponseResultList.get(i).id().isEmpty())
                .mapToObj(i -> userLocationTransactionalService.updateUserLocation(
                                currentUser,
                                userLocationList.get(i),
                                kakaoResponseResultList.get(i),
                                locationNameAndCategoryList.get(i).category()
                        )
                )
                .toList();
    }
}
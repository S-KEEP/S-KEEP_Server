package Skeep.backend.location.userLocation.service;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.domain.UserCategoryRepository;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.exception.GlobalErrorCode;
import Skeep.backend.location.location.domain.Location;
import Skeep.backend.location.userLocation.domain.UserLocation;
import Skeep.backend.location.userLocation.dto.request.UserLocationImagePatchDto;
import Skeep.backend.location.userLocation.dto.request.UserLocationPatchListDto;
import Skeep.backend.location.userLocation.dto.request.UserLocationPatchWithCategoryDto;
import Skeep.backend.location.userLocation.dto.response.*;
import Skeep.backend.location.userLocation.exception.UserLocationErrorCode;
import Skeep.backend.s3.service.S3Service;
import Skeep.backend.screenshot.dto.request.ScreenshotUploadDto;
import Skeep.backend.screenshot.service.ScreenshotService;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserLocationService {
    private final ScreenshotService screenshotService;
    private final UserFindService userFindService;
    private final UserLocationRetriever userLocationRetriever;
    private final UserLocationUpdater userLocationUpdater;
    private final UserLocationRemover userLocationRemover;
    private final S3Service s3Service;
    private final UserCategoryRepository userCategoryRepository;

    public UserLocationListDto getUserLocationListByUserCategory(
            Long userId,
            String userCategory,
            int page
    ) {
        // Request 검증 로직
        User currentUser = userFindService.findUserByIdAndStatus(userId);

        if (page < 1)
            throw BaseException.type(UserLocationErrorCode.INVALID_PAGE_USER_LOCATION);
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<UserLocation> userLocationPage
                = userLocationRetriever.findAllByUserIdAndUserCategory(
                                                currentUser.getId(),
                                                userCategory,
                                                pageable
                                        );
        if (page > userLocationPage.getTotalPages())
            throw BaseException.type(UserLocationErrorCode.INVALID_PAGE_USER_LOCATION);

        List<UserLocation> userLocationList = userLocationPage.getContent();

        // TODO: query 계속 날리는 부분 추후에 성능 개선
        List<UserLocationDto> userLocationDtoList =
                userLocationList.stream().map(
                        userLocation ->
                            UserLocationDto.of(
                                userLocation.getId(),
                                s3Service.getPresignUrl(userLocation.getFileName()),
                                LocationDto.of(userLocation.getLocation()),
                                UserCategoryDto.of(userLocation.getUserCategory())
                            )
                ).toList();

        return UserLocationListDto.of(
                userLocationDtoList,
                userLocationPage.getTotalElements(),
                userLocationPage.getTotalPages()
        );
    }

    public UserLocationCreate createUserLocation(
            Long userId,
            ScreenshotUploadDto screenshotUploadDto
    ) {
        User currentUser = userFindService.findUserByIdAndStatus(userId);
        List<UserLocation> userLocationList
                = screenshotService.analyzeImageAndSaveResult(currentUser, screenshotUploadDto);

        String uriString = userLocationList.stream()
                .map(userLocation -> "/user-location/" + userLocation.getId())
                .collect(Collectors.joining(","));

        List<UserLocationDto> userLocationDtoList =
                userLocationList.stream().map(
                        userLocation ->
                            UserLocationDto.of(
                                userLocation.getId(),
                                s3Service.getPresignUrl(userLocation.getFileName()),
                                LocationDto.of(userLocation.getLocation()),
                                UserCategoryDto.of(userLocation.getUserCategory())
                            )
                ).toList();

        return UserLocationCreate.of(
                UserLocationCreateDto.of(
                        userLocationDtoList,
                        userLocationList.size(),
                        screenshotUploadDto.getFile().size() - userLocationList.size()
                ),
                URI.create(uriString)
        );
    }

    public UserLocationPatchDto updateUserLocation(
            Long userId,
            UserLocationPatchListDto userLocationPatchListDto
    ) {
        User currentUser = userFindService.findUserByIdAndStatus(userId);

        List<UserLocationImagePatchDto> userLocationImagePatchDtoList
                = userLocationPatchListDto.userLocationImagePatchDtoList();

        List<UserLocation> userLocationList =
                userLocationImagePatchDtoList.stream().map(obj -> {
                        UserLocation targetUserLocation =
                                userLocationRetriever.findByUserAndId(currentUser, obj.id());

                        URI targetUri;
                        try {
                            targetUri = new URI(obj.imageUrl());
                        } catch (URISyntaxException e) {
                            throw BaseException.type(UserLocationErrorCode.BAD_REQUEST_IMAGE_URL);
                        }

                        String targetFileName = targetUri.getPath().substring(1);

                        if (!targetUserLocation.getFileName().equals(targetFileName))
                            throw BaseException.type(
                                    UserLocationErrorCode.MISMATCH_USER_LOCATION_AND_REQUEST_IMAGE_URL
                            );
                        return targetUserLocation;
                })
                .toList();

        List<UserLocation> retUserLocationList =
                screenshotService.reanalyzeImageAndSaveResult(
                        currentUser,
                        userLocationList,
                        userLocationPatchListDto.userLocationImagePatchDtoList()
                );

        List<UserLocationDto> userLocationDtoList =
                retUserLocationList.stream().map(
                        userLocation ->
                                UserLocationDto.of(
                                        userLocation.getId(),
                                        s3Service.getPresignUrl(userLocation.getFileName()),
                                        LocationDto.of(userLocation.getLocation()),
                                        UserCategoryDto.of(userLocation.getUserCategory())
                                )
                ).toList();

        return UserLocationPatchDto.of(
                userLocationDtoList,
                retUserLocationList.size(),
                userLocationList.size() - retUserLocationList.size()
        );
    }

    public UserLocationDto getUserLocationRetrieve(Long userId, Long userLocationId) {
        User currentUser = userFindService.findUserByIdAndStatus(userId);

        UserLocation targetLocation
                = userLocationRetriever.findByUserAndId(currentUser, userLocationId);
        Location location = targetLocation.getLocation();
        UserCategory userCategory = targetLocation.getUserCategory();

        return UserLocationDto.of(
                userLocationId,
                s3Service.getPresignUrl(targetLocation.getFileName()),
                LocationDto.of(location),
                UserCategoryDto.of(userCategory)
        );
    }

    @Transactional
    public Boolean updateUserLocationWithUserCategory(
            Long userId,
            Long userLocationId,
            UserLocationPatchWithCategoryDto userLocationPatchWithCategoryDto
    ) {
        User currentUser = userFindService.findUserByIdAndStatus(userId);
        UserLocation targetUserLocation = userLocationRetriever.findByUserAndId(currentUser, userLocationId);

        // ToDo: UserCategoryRetriever 구현되면 그때 코드 수정할 것
        UserCategory targetUserCategory = userCategoryRepository.findById(userLocationPatchWithCategoryDto.userCategoryId())
                .orElseThrow(() -> BaseException.type(GlobalErrorCode.NOT_FOUND));

        userLocationUpdater.updateUserCategory(targetUserLocation, targetUserCategory);

        return Boolean.TRUE;
    }

    @Transactional
    public void deleteUserLocation(Long userId, Long userLocationId) {
        User currentUser = userFindService.findUserByIdAndStatus(userId);

        if (userLocationRetriever.existsByUserAndId(currentUser, userLocationId))
            userLocationRemover.deleteByUserIdAndId(currentUser, userLocationId);
        else
            throw BaseException.type(UserLocationErrorCode.MISMATCH_USER_AND_USER_LOCATION);
    }
}

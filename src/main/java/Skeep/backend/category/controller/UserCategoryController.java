package Skeep.backend.category.controller;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.dto.UserCategoryDto;
import Skeep.backend.category.dto.request.UserCategoryCreateRequest;
import Skeep.backend.category.dto.response.UserCategoryList;
import Skeep.backend.category.service.*;
import Skeep.backend.global.annotation.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserCategoryController {
    private final UserCategoryRetriever userCategoryRetriever;
    private final UserCategoryUpdater userCategoryUpdater;
    private final UserCategoryRemover userCategoryRemover;
    private final UserCategorySaver userCategorySaver;
    private final UserCategoryService userCategoryService;

    @GetMapping("/user-category/list")
    public ResponseEntity<UserCategoryList> getUserCategoryList(@UserId Long userId) {
        return ResponseEntity.ok(userCategoryRetriever.getUserCategoryList(userId));
    }

    @PatchMapping("/user-category")
    public ResponseEntity<Void> updateUserCategory(
            @UserId Long userId,
            @RequestBody @Valid UserCategoryDto userCategoryDto
    ) {
        userCategoryUpdater.updateUserCategory(userId, userCategoryDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user-category/{userCategoryId}")
    public ResponseEntity<Void> deleteUserCategory(
            @UserId Long userId,
            @PathVariable("userCategoryId") Long userCategoryId
    ) {
        userCategoryRemover.deleteByUserIdAndUserCategoryId(userId, userCategoryId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user-category")
    public ResponseEntity<Void> saveUserCategory(
            @UserId Long userId,
            @RequestBody @Valid UserCategoryCreateRequest request
    ) {
        UserCategory userCategory = userCategorySaver.saveUserCategory(userId, request.name(), request.description());
        return ResponseEntity.created(URI.create("/api/user-location?page=1&userCategoryId=" + userCategory.getId())).build();
    }

    @GetMapping("/user/{targetId}/user-category")
    public ResponseEntity<?> getFriendUserCategoryList(
            @UserId Long userId,
            @PathVariable("targetId") Long targetId
    ) {
        return ResponseEntity.ok(
                userCategoryService.getFriendUserCategoryList(userId, targetId)
        );
    }
}

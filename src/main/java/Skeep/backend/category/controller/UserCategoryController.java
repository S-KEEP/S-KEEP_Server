package Skeep.backend.category.controller;

import Skeep.backend.category.dto.UserCategoryDto;
import Skeep.backend.category.dto.response.UserCategoryList;
import Skeep.backend.category.service.UserCategoryRemover;
import Skeep.backend.category.service.UserCategoryRetriever;
import Skeep.backend.category.service.UserCategoryUpdater;
import Skeep.backend.global.annotation.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-category")
public class UserCategoryController {
    private final UserCategoryRetriever userCategoryRetriever;
    private final UserCategoryUpdater userCategoryUpdater;
    private final UserCategoryRemover userCategoryRemover;

    @GetMapping("/list")
    public ResponseEntity<UserCategoryList> getUserCategoryList(@UserId Long userId) {
        return ResponseEntity.ok(userCategoryRetriever.getUserCategoryList(userId));
    }

    @PatchMapping("")
    public ResponseEntity<Void> updateUserCategory(
            @UserId Long userId,
            @RequestBody @Valid UserCategoryDto userCategoryDto
    ) {
        userCategoryUpdater.updateUserCategory(userId, userCategoryDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userCategoryId}")
    public ResponseEntity<Void> deleteUserCategory(
            @UserId Long userId,
            @PathVariable("userCategoryId") Long userCategoryId
    ) {
        userCategoryRemover.deleteUserCategory(userId, userCategoryId);
        return ResponseEntity.ok().build();
    }
}

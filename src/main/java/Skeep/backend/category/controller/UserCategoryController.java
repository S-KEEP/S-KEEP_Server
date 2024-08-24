package Skeep.backend.category.controller;

import Skeep.backend.category.dto.UserCategoryDto;
import Skeep.backend.category.dto.response.UserCategoryList;
import Skeep.backend.category.service.UserCategoryRemover;
import Skeep.backend.category.service.UserCategoryRetriever;
import Skeep.backend.category.service.UserCategoryUpdater;
import Skeep.backend.global.annotation.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
            @PathVariable Long userCategoryId
    ) {
//        userCategoryRemover.deleteUserCategory(userId, userCategoryId);
//        return ResponseEntity.ok().build();
        try {
            userCategoryRemover.deleteUserCategory(userId, userCategoryId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            // 예외 발생 시 BAD_REQUEST 응답을 반환
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            // 그 외의 예외 발생 시 INTERNAL_SERVER_ERROR 응답을 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

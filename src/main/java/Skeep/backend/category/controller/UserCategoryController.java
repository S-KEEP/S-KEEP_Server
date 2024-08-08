package Skeep.backend.category.controller;

import Skeep.backend.category.dto.UserCategoryList;
import Skeep.backend.category.service.UserCategoryService;
import Skeep.backend.global.annotation.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userCategory")
public class UserCategoryController {
    private final UserCategoryService userCategoryService;

    @GetMapping("/list")
    public ResponseEntity<UserCategoryList> getUserCategoryList(@UserId Long userId) {
        return ResponseEntity.ok(userCategoryService.getUserCategoryList(userId));
    }
}

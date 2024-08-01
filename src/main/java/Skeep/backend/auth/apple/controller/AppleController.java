package Skeep.backend.auth.apple.controller;

import Skeep.backend.auth.apple.dto.AppleLoginRequest;
import Skeep.backend.auth.apple.service.AppleService;
import Skeep.backend.global.dto.JwtDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/apple")
public class AppleController {
    private final AppleService appleService;

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody @Valid AppleLoginRequest request) {
        return ResponseEntity.ok(appleService.login(request));
    }

    @PostMapping("/revoke")
    public void revoke(@RequestBody @Valid AppleLoginRequest request) {
        appleService.revokeAppleUser(request);
    }
}

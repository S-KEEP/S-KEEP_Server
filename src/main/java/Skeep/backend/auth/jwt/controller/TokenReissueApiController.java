package Skeep.backend.auth.jwt.controller;

import Skeep.backend.auth.jwt.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/jwt/reissue")
public class TokenReissueApiController {
    private final JwtTokenService jwtTokenService;

    @PostMapping
    public ResponseEntity<String> reissueToken(@RequestParam("refreshToken") String refreshToken) {
        String newAccessToken = jwtTokenService.reissueAccessToken(refreshToken);
        return ResponseEntity.ok(newAccessToken);
    }
}

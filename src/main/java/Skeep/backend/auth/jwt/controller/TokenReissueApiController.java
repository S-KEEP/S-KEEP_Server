package Skeep.backend.auth.jwt.controller;

import Skeep.backend.auth.jwt.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/jwt/reissue")
public class TokenReissueApiController {
    private final JwtTokenService jwtTokenService;

    @PostMapping
    public ResponseEntity<String> reissueToken(@RequestBody String refreshToken) {
        String newAccessToken = jwtTokenService.reissueAccessToken(refreshToken);
        return ResponseEntity.ok(newAccessToken);
    }
}

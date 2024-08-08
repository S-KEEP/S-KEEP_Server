package Skeep.backend.auth.jwt.controller;

import Skeep.backend.auth.jwt.dto.NewAccessTokenResponse;
import Skeep.backend.auth.jwt.dto.RefreshTokenRequest;
import Skeep.backend.auth.jwt.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/jwt/reissue")
public class TokenReissueApiController {
    private final JwtTokenService jwtTokenService;

    @PostMapping
    public ResponseEntity<NewAccessTokenResponse> reissueToken(@RequestBody RefreshTokenRequest request) {
        String newAccessToken = jwtTokenService.reissueAccessToken(request.refreshToken());
        return ResponseEntity.ok(new NewAccessTokenResponse(newAccessToken));
    }
}

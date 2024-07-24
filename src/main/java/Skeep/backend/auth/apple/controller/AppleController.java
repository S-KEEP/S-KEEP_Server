package Skeep.backend.auth.apple.controller;

import Skeep.backend.auth.apple.dto.AppleLoginRequest;
import Skeep.backend.auth.apple.service.AppleService;
import Skeep.backend.global.dto.JwtDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/apple")
public class AppleController {
    private final AppleService appleService;

    @PostMapping("/login")
    private JwtDto login(@RequestBody @Valid AppleLoginRequest request) throws AuthenticationException, NoSuchAlgorithmException, InvalidKeySpecException, JsonProcessingException {
        return appleService.login(request);
    }
}

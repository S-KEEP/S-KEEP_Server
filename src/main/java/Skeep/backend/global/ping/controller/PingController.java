package Skeep.backend.global.ping.controller;

import Skeep.backend.auth.apple.service.AppleService;
import Skeep.backend.global.dto.JwtDto;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.service.UserFindService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ping")
public class PingController {
    private final UserFindService userFindService;
    private final AppleService appleService;

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody @Valid String serialId) {
        User user = userFindService.findUserBySerialId(serialId);
        JwtDto jwtDto = appleService.createJwtDto(user.getId(), user.getRole());
        return ResponseEntity.ok(jwtDto);
    }
}

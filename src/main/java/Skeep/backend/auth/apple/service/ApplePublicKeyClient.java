package Skeep.backend.auth.apple.service;

import Skeep.backend.auth.apple.dto.ApplePublicKeys;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "applePublicKeyClient", url = "https://appleid.apple.com/auth")
public interface ApplePublicKeyClient {
    @GetMapping(value = "/keys")
    ApplePublicKeys getAppleAuthPublicKey();
}
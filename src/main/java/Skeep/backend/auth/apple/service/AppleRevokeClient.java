package Skeep.backend.auth.apple.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "appleAuthClient", url = "https://appleid.apple.com/auth")
public interface AppleRevokeClient {
    @PostMapping(value = "/revoke", consumes = "application/x-www-form-urlencoded")
    void revokeToken(@RequestParam("client_secret") String clientSecret,
                     @RequestParam("token") String refreshToken,
                     @RequestParam("client_id") String clientId
    );
}
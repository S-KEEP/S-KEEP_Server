package Skeep.backend.auth.apple.service;

import Skeep.backend.auth.apple.dto.AppleAuthTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "appleAuthClient", url = "https://appleid.apple.com/auth")
public interface AppleOAuthTokenClient {
    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    AppleAuthTokenResponse generateAuthToken(@RequestParam("code") String authorizationCode,
                                             @RequestParam("client_id") String clientId,
                                             @RequestParam("client_secret") String clientSecret,
                                             @RequestParam("grant_type") String grantType);
}
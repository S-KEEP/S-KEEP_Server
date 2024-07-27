package Skeep.backend.global.security.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationManager implements AuthenticationManager {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("AuthenticationManager 진입");
        return jwtAuthenticationProvider.authenticate(authentication);
    }
}

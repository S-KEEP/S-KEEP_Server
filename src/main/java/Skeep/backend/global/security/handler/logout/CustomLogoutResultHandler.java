package Skeep.backend.global.security.handler.logout;

import Skeep.backend.global.security.info.AuthenticationResponse;
import Skeep.backend.user.exception.UserErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomLogoutResultHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException {
        if (authentication == null) {
            log.info("인증 정보가 존재하지 않습니다. authentication is null.");
            AuthenticationResponse.makeFailureResponse(response, UserErrorCode.NOT_FOUND_USER);
        }
        AuthenticationResponse.makeSuccessResponse(response);
    }
}
package Skeep.backend.global.security.handler.exception;

import Skeep.backend.global.exception.ErrorCode;
import Skeep.backend.global.exception.GlobalErrorCode;
import Skeep.backend.global.security.info.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authenticationException) throws IOException {
        ErrorCode errorCode = (ErrorCode) request.getAttribute("exception");
        if (errorCode == null) {
            AuthenticationResponse.makeFailureResponse(response, GlobalErrorCode.VALIDATION_ERROR);
            return;
        }
        AuthenticationResponse.makeFailureResponse(response, errorCode);
    }
}

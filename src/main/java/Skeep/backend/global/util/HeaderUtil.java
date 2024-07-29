package Skeep.backend.global.util;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.exception.GlobalErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

import java.util.Optional;

public class HeaderUtil {
    public static Optional<String> refineHeader(HttpServletRequest request, String headerName, String prefix) {
        String headerValue = request.getHeader(headerName);
        if (!StringUtils.hasText(headerValue) || !headerValue.startsWith(prefix))
            throw new BaseException(GlobalErrorCode.INVALID_HEADER_VALUE);
        return Optional.of(headerValue.substring(prefix.length()));
    }
}
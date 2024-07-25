package Skeep.backend.global.security.info;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONValue;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationResponse {
    public static void makeSuccessResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());

        Map<String, Object> body = new HashMap<>();
        body.put("success", "true");
        body.put("data", null);
        body.put("error", null);

        response.getWriter().write(JSONValue.toJSONString(body));
    }

    public static void makeFailureResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getStatus().value());

        Map<String, Object> body= new HashMap<>();
        body.put("success", false);
        body.put("data", null);
        body.put("error", BaseException.type(errorCode));

        response.getWriter().write(JSONValue.toJSONString(body));
    }
}
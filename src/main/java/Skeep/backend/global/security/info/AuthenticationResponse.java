package Skeep.backend.global.security.info;

import Skeep.backend.global.exception.ErrorCode;
import Skeep.backend.global.exception.GlobalErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONValue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationResponse {
    public static void makeSuccessResponse(HttpServletResponse response) throws IOException {

        ErrorCode successCode = GlobalErrorCode.SUCCESS;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(successCode.getStatus().value());

        Map<String, Object> body = new HashMap<>();
        body.put("errorCode", successCode.getErrorCode());
        body.put("message", successCode.getMessage());
        body.put("result", null);

        response.getWriter().write(JSONValue.toJSONString(body));
    }

    public static void makeFailureResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getStatus().value());

        Map<String, Object> body= new HashMap<>();
        body.put("errorCode", errorCode.getErrorCode());
        body.put("message", errorCode.getMessage());
        body.put("result", null);

        response.getWriter().write(JSONValue.toJSONString(body));
    }
}
package Skeep.backend.global.constant;

import java.util.List;

public class Constants {
    public static String CLAIM_USER_ID = "uuid";
    public static String PREFIX_BEARER = "Bearer ";
    public static String PREFIX_AUTH = "authorization";

    public static List<String> NO_NEED_AUTH = List.of(
            "/api/auth/apple/login",
            "/swagger-ui/index.html",
            "/swagger-ui/swagger-ui-standalone-preset.js",
            "/swagger-ui/swagger-initializer.js",
            "/swagger-ui/swagger-ui-bundle.js",
            "/swagger-ui/swagger-ui.css",
            "/swagger-ui/index.css",
            "/swagger-ui/favicon-32x32.png",
            "/swagger-ui/favicon-16x16.png",
            "/api-docs/json/swagger-config",
            "/api-docs/json",
            "/h2-console/**",
            "/favicon.ico",
            "/error",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v1/api-docs/**",
            "/v2/api-docs/**",
            "/v3/api-docs/**",
            "/v3/api-docs/swagger-config"
    );
}

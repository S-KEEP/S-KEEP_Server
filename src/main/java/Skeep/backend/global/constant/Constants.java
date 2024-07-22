package Skeep.backend.global.constant;

import java.util.List;

public class Constants {
    public static String CLAIM_USER_ID = "uuid";
    public static String PREFIX_BEARER = "Bearer ";
    public static String PREFIX_AUTH = "authorization";

    public static List<String> NO_NEED_AUTH = List.of(
            "/api/oauth2/sign-up",
            "/api/oauth2/login/kakao",
            "/api/oauth2/login/apple"
    );
}

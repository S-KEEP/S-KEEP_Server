## SKEEP에 애플 탈퇴 적용하기
### Flow
1. 클라이언트는 탈퇴를 진행하기 위해 애플 소셜로그인 진행
2. 클라이언트는 서버에게 authorizationCode를 넘김
3. 서버는 애플 측에게 accessToken을 받아오는 REST API를 요청함
4. 서버는 애플 측에게 accessToken을 받아서 또 애플 측에게 탈퇴 REST API를 요청함
5. 애플 REST API에서 200이 내려오면 서버는 자체 회원 탈퇴(답변 삭제 및 유저 컬럼 업데이트) 진행

### client_secret 만들기
client_secret은 다음과 같은 내용을 포함하여 JWT형식의 토큰을 생성해서 만든다.

```json
{
    "alg": "ES256", // ES255을 사용한다.
    "kid": "ABC123DEFG" // Apple Developer 페이지에 명시되어있는 Key ID (10-character, Sign In with Apple)
}

{
    "iss": "DEF123GHIJ", // Apple Developer 페이지에 명시되어있는 Team ID (10-character)
    "iat": 1437179036, // client secret이 생성된 일시를 입력 (현재시간을 주면 된다)
    "exp": 1493298100, // client secret이 만료될 일시를 입력. (현재시간으로 부터 15777000초, 즉 6개월을 초과하면 안된다.)
    "aud": "https://appleid.apple.com", // "https://appleid.apple.com" 값 고정
    "sub": "com.mytest.app" // App의 Bundle ID 값을 입력. ex) com.xxx.xxx 와 같은 형식
}
```

### 애플 서버에서 accessToken, refreshToken 받아오기
AppleOAuthTokenClient.java
```java
@FeignClient(name = "appleAuthClient", url = "https://appleid.apple.com/auth")
public interface AppleOAuthTokenClient {
    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    AppleAuthTokenResponse generateAuthToken(@RequestParam("code") String authorizationCode,
                                             @RequestParam("client_id") String clientId,
                                             @RequestParam("client_secret") String clientSecret,
                                             @RequestParam("grant_type") String grantType);
}
```

### 애플 탈퇴 API 요청
```java
@FeignClient(name = "appleAuthClient", url = "https://appleid.apple.com/auth")
public interface AppleRevokeClient {
    @PostMapping(value = "/revoke", consumes = "application/x-www-form-urlencoded")
    void revokeToken(@RequestParam("client_secret") String clientSecret,
                     @RequestParam("token") String refreshToken,
                     @RequestParam("client_id") String clientId
    );
}
```

### 참고 블로그
https://velog.io/@givepro91/jjo2cyus  
https://kedric-me.tistory.com/entry/JAVA-%EC%95%A0%ED%94%8C-%EC%97%B0%EB%8F%99%ED%95%B4%EC%A0%9C-%EA%B5%AC%ED%98%84-Sign-Out-of-Apple-ID  

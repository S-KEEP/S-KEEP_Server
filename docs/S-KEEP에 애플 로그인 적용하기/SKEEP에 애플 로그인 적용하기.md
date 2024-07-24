## SKEEP에 애플 로그인 적용하기
### 요약
어플리케이션에서 로그인을 하기 때문에 그 이후에 백엔드에서 처리해야 할 로직을 작성해야 한다.
> 1️⃣ 사용자가 정보 허용 범위, Id, Password를 입력한다.  
> 2️⃣ 위의 값들과 함께 Apple Server로 요청을 보내면 Apple Server에서 사용자 정보를 담은 Identity Token을 제공한다.  
> 3️⃣ 해당 Identity Token와 함께 서버(Apple Server 아님)에 로그인 / 회원가입 api 호출을 한다.  
> 
> **여기서 서버에서 처리해야하는 부분은 3️⃣이다.**

![img_2.png](img_2.png)  
(출처: https://hwannny.tistory.com/71)

### 0) 기본 설정
```yml
apple:
  auth:
    token-url: https://appleid.apple.com/auth/token 
    public-key-url: https://appleid.apple.com/auth/keys
  redirect-uri: ${APPLE_REDIRECT_URL} # 설정한 redirect-uri
  iss: https://appleid.apple.com
  aud: ${APPLE_CLIENT_ID} # Services의 Identifier
  team-id: ${APPLE_TEAM_ID} # App ID의 Prefix
  key:
    id: ${APPLE_KEY_ID} # Key ID
    path: classpath:/apple/AuthKey_${APPLE_KEY_ID}.p8
```

### 1) 어플리케이션에서 로그인을 성공한 이후 다음과 같은 정보를 가지고 있는다.
![img.png](img.png)
```json
{
  "state":"test",
  "code":"c50d317be38c742c0beb19d8743de014c.0.nruy.1NtQvAmp9uhyrsMj1mp7kg",
  "id_token":"eyJraWQiOiI4NkQ4OEtmIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiY29tLndoaXRlcGFlay5zZXJ2aWNlcyIsImV4cCI6MTU5ODgwMDEyOCwiaWF0IjoxNTk4Nzk5NTI4LCJzdWIiOiIwMDAxNDguZjA2ZDgyMmNlMGIyNDgzYWFhOTdkMjczYjA5NzgzMjUuMTcxNyIsIm5vbmNlIjoiMjBCMjBELTBTOC0xSzgiLCJjX2hhc2giOiJ1aFFiV0gzQUFWdEc1OUw4eEpTMldRIiwiZW1haWwiOiJpNzlmaWl0OWIzQHByaXZhdGVyZWxheS5hcHBsZWlkLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjoidHJ1ZSIsImlzX3ByaXZhdGVfZW1haWwiOiJ0cnVlIiwiYXV0aF90aW1lIjoxNTk4Nzk5NTI4LCJub25jZV9zdXBwb3J0ZWQiOnRydWV9.GQBCUHza0yttOfpQ-J5OvyZoGe5Zny8pI06sKVDIJaQY3bdiphllg1_pHMtPUp7FLv3ccthcmqmZn7NWVoIPkc9-_8squ_fp9F68XM-UsERKVzBvVR92TwQuKOPFr4lRn-2FlBzN4NegicMS-IV8Ad3AKTIRMIhvAXG4UgNxgPAuCpHwCwEAJijljfUfnRYO-_ywgTcF26szluBz9w0Y1nn_IIVCUzAwYiEMdLo53NoyJmWYFWu8pxmXRpunbMHl5nvFpf9nK-OGtMJrmZ4DlpTc2Gv64Zs2bwHDEvOyQ1WiRUB6_FWRH5FV10JSsccMlm6iOByOLYd03RRH2uYtFw",
  "user":{
    "email":"i79fiit9b3@privaterelay.appleid.com",
    "name":{
    "firstName":"SEUNGJOO",
    "lastName":"PAEK"
    }
  }
}
```
- identityToken
  - JWT 형식이다.
  - 생성 후 10분 내에 만료된다.
  - **Payload에 속한 값들이 변조되지 않았는지 검증하기 위해** 애플 서버의 Public key를 사용해서 JWS E256 signature를 검증한다.
  - Public Key로 사용자 식별값을 추출한다.
- authorizationCode
  - 이 code와 다른 추가적인 정보를 이용하여 refresh_token, access_token을 발급받는다.
- user
  - 여기서 user에 대한 데이터는 처음 로그인 할 때만 주기 때문에 저장할 계획이 있다면 처음에 잘 받아서 저장해야 한다.

### 2) idendtityToken을 검증하기 위한 Public Key를 발급받는다.
발급받기 위해서 Apple identityToken에서 **암호화 알고리즘인 ALG와 키 아이디인 KID 헤더**를 추출해야 한다.  
```java
@Component
public class JwtParser {
    private static final String TOKEN_VALUE_DELIMITER = "\\.";
    private static final int HEADER_INDEX = 0;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public Map<String, String> parseHeaders(String token) {
        try {
            String encodedHeader = token.split(TOKEN_VALUE_DELIMITER)[HEADER_INDEX];
            String decodedHeader = new String(Base64Utils.decodeFromUrlSafeString(encodedHeader));
            return OBJECT_MAPPER.readValue(decodedHeader, Map.class);
        } catch (JsonProcessingException | ArrayIndexOutOfBoundsException e) {
            throw UnauthorizedException.invalid();
        }
    }
}
```

해당 url을 통해 공개키를 요청한다.  
요청하면 다음과 같은 결과를 받는다.  
```json
{
  "keys": [
    {
      "kty": "RSA",
      "kid": "fh6Bs8C",
      "use": "sig",
      "alg": "RS256",
      "n": "u704gotMSZc6CSSVNCZ1d0S9dZKwO2BVzfdTKYz8wSNm7R_KIufOQf3ru7Pph1FjW6gQ8zgvhnv4IebkGWsZJlodduTC7c0sRb5PZpEyM6PtO8FPHowaracJJsK1f6_rSLstLdWbSDXeSq7vBvDu3Q31RaoV_0YlEzQwPsbCvD45oVy5Vo5oBePUm4cqi6T3cZ-10gr9QJCVwvx7KiQsttp0kUkHM94PlxbG_HAWlEZjvAlxfEDc-_xZQwC6fVjfazs3j1b2DZWsGmBRdx1snO75nM7hpyRRQB4jVejW9TuZDtPtsNadXTr9I5NjxPdIYMORj9XKEh44Z73yfv0gtw",
      "e": "AQAB"
    },
    {
      "kty": "RSA",
      "kid": "W6WcOKB",
      "use": "sig",
      "alg": "RS256",
      "n": "2Zc5d0-zkZ5AKmtYTvxHc3vRc41YfbklflxG9SWsg5qXUxvfgpktGAcxXLFAd9Uglzow9ezvmTGce5d3DhAYKwHAEPT9hbaMDj7DfmEwuNO8UahfnBkBXsCoUaL3QITF5_DAPsZroTqs7tkQQZ7qPkQXCSu2aosgOJmaoKQgwcOdjD0D49ne2B_dkxBcNCcJT9pTSWJ8NfGycjWAQsvC8CGstH8oKwhC5raDcc2IGXMOQC7Qr75d6J5Q24CePHj_JD7zjbwYy9KNH8wyr829eO_G4OEUW50FAN6HKtvjhJIguMl_1BLZ93z2KJyxExiNTZBUBQbbgCNBfzTv7JrxMw",
      "e": "AQAB"
    },
    {
      "kty": "RSA",
      "kid": "YuyXoY",
      "use": "sig",
      "alg": "RS256",
      "n": "1JiU4l3YCeT4o0gVmxGTEK1IXR-Ghdg5Bzka12tzmtdCxU00ChH66aV-4HRBjF1t95IsaeHeDFRgmF0lJbTDTqa6_VZo2hc0zTiUAsGLacN6slePvDcR1IMucQGtPP5tGhIbU-HKabsKOFdD4VQ5PCXifjpN9R-1qOR571BxCAl4u1kUUIePAAJcBcqGRFSI_I1j_jbN3gflK_8ZNmgnPrXA0kZXzj1I7ZHgekGbZoxmDrzYm2zmja1MsE5A_JX7itBYnlR41LOtvLRCNtw7K3EFlbfB6hkPL-Swk5XNGbWZdTROmaTNzJhV-lWT0gGm6V1qWAK2qOZoIDa_3Ud0Gw",
      "e": "AQAB"
    }
  ]
}
```
이 중 `ALG`와 `KID`가 동일한 키의 `n`, `e`로 KeySpec을 만든다.
```java
@Component
public class AppleOAuthPublicKeyGenerator {
    private static final String ALG_HEADER_KEY = "alg";
    private static final String KID_HEADER_KEY = "kid";
    private static final int POSITIVE_SIGNUM = 1;

    public PublicKey generatePublicKey(Map<String, String> tokenHeaders, ApplePublicKeys applePublicKeys) {
        List<ApplePublicKey> publicKeys = applePublicKeys.getKeys();
        ApplePublicKey publicKey = publicKeys.stream()
                .filter(key -> key.getAlg().equals(tokenHeaders.get(ALG_HEADER_KEY)))
                .filter(key -> key.getKid().equals(tokenHeaders.get(KID_HEADER_KEY)))
                .findAny()
                .orElseThrow(UnauthorizedException::invalid);

        return generatePublicKeyWithApplePublicKey(publicKey);
    }

    private PublicKey generatePublicKeyWithApplePublicKey(ApplePublicKey applePublicKey) {
        byte[] n = Base64Utils.decodeFromUrlSafeString(applePublicKey.getN());
        byte[] e = Base64Utils.decodeFromUrlSafeString(applePublicKey.getE());
        RSAPublicKeySpec publicKeySpec =
                new RSAPublicKeySpec(new BigInteger(POSITIVE_SIGNUM, n), new BigInteger(POSITIVE_SIGNUM, e));

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(applePublicKey.getKty());
            return keyFactory.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
            throw new ExternalServerException("응답 받은 Apple Public Key로 PublicKey를 생성할 수 없습니다.");
        }
    }
}
```

![img_3.png](img_3.png)  
(출처: https://developer.apple.com/documentation/sign_in_with_apple/sign_in_with_apple_rest_api/verifying_a_user)



### 3) identityToken을 검증한다.
2번에서 생성된 Public Key로 사용자 식별 값을 추출한다.  
다음의 코드에서 추출된 `Claim`의 `Sub`가 **Apple에서 제공하는 사용자 식별값**이다.

검증 코드 예시 1)
```java
@Component
public class AppleOAuthClient implements OAuthClient {

    private final JwtParser jwtParser;
    private final AppleApiCaller appleApiCaller;
    private final AppleOAuthPublicKeyGenerator appleOAuthPublicKeyGenerator;
    private final AppleJwtClaimValidator appleJwtClaimValidator;

    // 생성자

    @Override
    public String getOAuthMemberId(String idToken) {
        Map<String, String> tokenHeaders = jwtParser.parseHeaders(idToken); // 1
        ApplePublicKeys applePublicKeys = appleApiCaller.getPublicKeys(); // 2
        PublicKey publicKey = appleOAuthPublicKeyGenerator.generatePublicKey(tokenHeaders, applePublicKeys); //3
        Claims claims = jwtParser.parseClaims(idToken, publicKey); //4
        validateClaims(claims); // 5
        return claims.getSubject();
    }

    private void validateClaims(Claims claims) {
        // ...
    }
}
```

검증 코드 예시 2)
```java
public boolean verifyIdentityToken(String id_token) {

  try {
    SignedJWT signedJWT = SignedJWT.parse(id_token);
    ReadOnlyJWTClaimsSet payload = signedJWT.getJWTClaimsSet();

  // EXP
    Date currentTime = new Date(System.currentTimeMillis());
    if (!currentTime.before(payload.getExpirationTime())) {
    	return false;
    }

    // NONCE(Test value), ISS, AUD
    if (!"20B20D-0S8-1K8".equals(payload.getClaim("nonce")) || !ISS.equals(payload.getIssuer()) || !AUD.equals(payload.getAudience().get(0))) {
    	return false;
    }

    // RSA
    if (verifyPublicKey(signedJWT)) {
    	return true;
    }
  } catch (ParseException e) {
  	e.printStackTrace();
  }

  return false;
}
```

### 4) 검증에 성공하면, Authorization Code로 Token을 발급받는다.
> https://developer.apple.com/documentation/sign_in_with_apple/generate_and_validate_tokens

- 필요한 값
  - code: App으로부터 넘겨 받은 Authorization Code
  - cliend_id: Apple Developer 페이지의 App Bundle ID
  - grant_type: authorization_code의 값
  - client_secret: client secret은 다음으로 생성 (https://developer.apple.com/documentation/accountorganizationaldatasharing/creating-a-client-secret)

✅ client_secret을 먼저 생성해야한다.
```java
public String createClientSecret() {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(KEY_ID).build();
        JWTClaimsSet claimsSet = new JWTClaimsSet();
        Date now = new Date();

        claimsSet.setIssuer(TEAM_ID);
        claimsSet.setIssueTime(now);
        claimsSet.setExpirationTime(new Date(now.getTime() + 3600000));
        claimsSet.setAudience(ISS);
        claimsSet.setSubject(AUD);

        SignedJWT jwt = new SignedJWT(header, claimsSet);

        try {
            ECPrivateKey ecPrivateKey = new ECPrivateKeyImpl(readPrivateKey());
            JWSSigner jwsSigner = new ECDSASigner(ecPrivateKey.getS());

            jwt.sign(jwsSigner);

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        return jwt.serialize();
}
```

위의 토큰 발급 요청을 필요한 값들음 담아 토큰 발급 요청을 하면 아래와 같은 응답을 받는다.
```
{
  "access_token":"a08c1600e80f84d44842ce3342abac413.0.mruy.IyMPSXmTYtMyUCDWDKKN3g",
  "expires_in":3600,
  "id_token":"eyJraWQiOiJlWGF1bm1MIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiY29tLndoaXRlcGFlay5zZXJ2aWNlcyIsImV4cCI6MTU5ODgwMjU2NiwiaWF0IjoxNTk4ODAxOTY2LCJzdWIiOiIwMDAxNDguZjA2ZDgyMmNlMGIyNDgzYWFhOTdkMjczYjA5NzgzMjUuMTcxNyIsIm5vbmNlIjoiMjBCMjBELTBTOC0xSzgiLCJhdF9oYXNoIjoiaFNMOFBrZWxoNWdFblNGeURISGNIQSIsImVtYWlsIjoiaTc5ZmlpdDliM0Bwcml2YXRlcmVsYXkuYXBwbGVpZC5jb20iLCJlbWFpbF92ZXJpZmllZCI6InRydWUiLCJpc19wcml2YXRlX2VtYWlsIjoidHJ1ZSIsImF1dGhfdGltZSI6MTU5ODgwMTk2Miwibm9uY2Vfc3VwcG9ydGVkIjp0cnVlfQ.WqYWPuTi8apdqQnP9V6-yvVLBt84P48mYVbGa0e3io4sNKL919iIVZfNoE1GZ8F6WNOrXtcOQU_n3hclrfmNyYsidj-IH6R-0JwxwLobKJoFNH7lfKd067OyiYGxHJMFcleRaDoRWsBF4Wh_FUT3Nft_qy2CVd9pNEg-mFOruI6-5oUDdnPQwelywNCsqlkmECcKna4Psvs9eRn58ALfpke5SL-A762--peGzgp00RvrGMK4t26UWG9UN13LIXDvX3ydMCdg8gvmO7BizSoi4zJHgvKuYxLLT_heOkvNWxcT81h7dEdwqAczLTE3FBarTkAekxvtykVwlEBlfyoXcg",
  "refresh_token":"r8e88bc9f62bc496398b71117610c5aeb.0.mruy.UuuL5tpwnWaof86XPErqJg",
  "token_type":"Bearer"
}
```
- id_token을 decode하여 필요한 유저 정보를 얻을 수 있다.
- access_token이 아니라 refresh_token을 사용하여 **애플과의 세션**을 유지한다.  
- refresh_token을 이용하여 access_token을 재발급받는다. (refresh_token은 만료기간이 없다.)  
![img_1.png](img_1.png)  
(출처: https://hwannny.tistory.com/71)  

> 따라서, refresh_token은 만료되지 않기 때문에 권한이 필요한 요청일 경우 기타 저장소에 저장해두 조회해서 사용하기도 한다....?

### 5) 서비스의 JWT 토큰을 발급한다.

### 추가) 전달받은 refresh_token의 유효성을 검증하고 싶다면 다음의 API를 따르면 된다.
- https://developer.apple.com/documentation/sign_in_with_apple/generate_and_validate_tokens

### ⭐️ 정리
그렇다면, 내가 구현해야하는 코드는 ...
> 1️⃣ identity_token, authorzation_code를 클라이언트로부터 받는다.  
> 2️⃣ identity_token에서 ALG, KID를 추출한다.  
> 3️⃣ Public Key를 발급받는다.  
> 4️⃣ identity_token을 Publike Key를 이용해 사용자 식별 값을 추출한다.  
>
> (로그인)  
> 5️⃣ 우리 서비스의 JWT 토큰을 발급한다. => Access Token을 주고 Refresh Token은 레디스에 저장한다.
> 
> (회원가입)  
> 5️⃣ client_secret을 생성한다.  
> 6️⃣ authorzation_code로 refresh_token을 발급받는다.  
> 7️⃣ refresh_token와 user 정보를 DB에 저장한다.  
> 8️⃣ 우리 서비스의 JWT 토큰을 발급한다. => Access Token을 주고 Refresh Token은 레디스에 저장한다.

### 참고 블로그
https://hwannny.tistory.com/71  
https://velog.io/@byeongju/Apple-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0  
https://whitepaek.tistory.com/61  
https://velog.io/@komment/Spring-Boot-OAuth-2.0-JWT%EB%A5%BC-%ED%99%9C%EC%9A%A9%ED%95%9C-%EC%86%8C%EC%85%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B5%AC%ED%98%84-2-%EC%95%A0%ED%94%8C%ED%8E%B8  


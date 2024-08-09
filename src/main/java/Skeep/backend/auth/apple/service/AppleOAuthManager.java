package Skeep.backend.auth.apple.service;

import Skeep.backend.auth.apple.dto.ApplePublicKey;
import Skeep.backend.auth.apple.dto.ApplePublicKeys;
import Skeep.backend.auth.exception.AuthErrorCode;
import Skeep.backend.global.exception.BaseException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMException;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AppleOAuthManager {
    @Value("${apple.team-id}")
    private String teamId;

    @Value("${apple.key.id}")
    private String keyId;

    @Value("${apple.key.path}")
    private String keyPath;

    @Value("${apple.aud}")
    private String aud;

    public PublicKey generatePublicKey(Map<String, String> tokenHeaders, ApplePublicKeys applePublicKeys) {
        ApplePublicKey publicKey = applePublicKeys.getMatchedKey(tokenHeaders.get("kid"), tokenHeaders.get("alg"));

        return getPublicKey(publicKey);
    }

    private PublicKey getPublicKey(ApplePublicKey publicKey){
        byte[] nBytes = Base64.getUrlDecoder().decode(publicKey.n());
        byte[] eBytes = Base64.getUrlDecoder().decode(publicKey.e());

        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(1, nBytes), new BigInteger(1, eBytes));

        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance(publicKey.kty());
        } catch (NoSuchAlgorithmException e) {
            throw new BaseException(AuthErrorCode.NOT_FOUND_ALGORITHM);
        }

        try {
            return keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException e) {
            throw new BaseException(AuthErrorCode.NOT_SUPPORTED_ALGORITHM);
        }
    }

    public String createClientSecret() {
        Date expirationDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant());
        Map<String, Object> jwtHeader = new HashMap<>();
        jwtHeader.put("kid", keyId);
        jwtHeader.put("alg", "ES256");

        return Jwts.builder()
                .setHeaderParams(jwtHeader)
                .setIssuer(teamId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .setAudience("https://appleid.apple.com")
                .setSubject(aud)
                .signWith(SignatureAlgorithm.ES256, getPrivateKey())
                .compact();
    }

    public PrivateKey getPrivateKey() {
        ClassPathResource resource = new ClassPathResource(keyPath);
        String privateKey = null;
        try {
            privateKey = new String(Files.readAllBytes(Paths.get(resource.getURI())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Reader pemReader = new StringReader(privateKey);
        PEMParser pemParser = new PEMParser(pemReader);
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();

        PrivateKeyInfo object = null;
        try {
            object = (PrivateKeyInfo) pemParser.readObject();
        } catch (IOException e) {
            throw new BaseException(AuthErrorCode.FAILED_TO_READ_PRIVATE_KEY);
        }

        try {
            return converter.getPrivateKey(object);
        } catch (PEMException e) {
            throw new BaseException(AuthErrorCode.PEM_PARSING_ERROR);
        }
    }
}
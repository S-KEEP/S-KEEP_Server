package Skeep.backend.global.util;

import Skeep.backend.global.constant.Constants;
import Skeep.backend.global.dto.JwtDto;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.exception.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.security.Key;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil implements InitializingBean {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token.expiration}")
    @Getter
    private Integer accessExpiration;

    @Value("${jwt.refresh-token.expiration}")
    @Getter
    private Integer refreshExpiration;

    private Key key;

    private static final String TOKEN_VALUE_DELIMITER = "\\.";
    private static final int HEADER_INDEX = 0;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(Long id, Integer expiration) {
        Claims claims = Jwts.claims();
        claims.put(Constants.CLAIM_USER_ID, id);

        return Jwts.builder()
                .setHeaderParam(Header.JWT_TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public JwtDto generateTokens(Long id) {
        return JwtDto.of(
                generateToken(id, accessExpiration),
                generateToken(id, refreshExpiration)
        );
    }

    public Map<String, String> parseHeaders(String token) throws JsonProcessingException {
        String encodedHeader = token.split(TOKEN_VALUE_DELIMITER)[HEADER_INDEX];
        String decodedHeader = new String(Base64.getUrlDecoder().decode(encodedHeader));

        return OBJECT_MAPPER.readValue(decodedHeader, Map.class);
    }

    public Claims getTokenClaims(String token, PublicKey publicKey) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
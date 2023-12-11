package com.example.libraryreservation.jwt;

import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import com.example.libraryreservation.model.UserModel;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;

@AllArgsConstructor
public class JwtUtil {
    @Value("${jwt.secret_key}")
    public static SecretKey secretKey;
    private static final Long accessExpiredMs = (long) (1000 * 60 * 30);
    private static final Long refreshExpiredMs = (long) (1000 * 60 * 60 * 3);

    public static String generateToken(UserModel userModel) {
        return createAccessToken(userModel.getPhoneNumber());
    }

    private static String createAccessToken(String phoneNumber)  {
        return Jwts.builder()
                .subject(phoneNumber)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessExpiredMs))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public static String createRefreshToken() {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshExpiredMs))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public static boolean isExpired(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public static String getSubject(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}

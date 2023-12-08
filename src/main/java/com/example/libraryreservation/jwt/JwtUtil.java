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

    public static String generateToken(UserModel userModel, Long expiredMs) {
        return createToken(userModel.getPhoneNumber(), expiredMs);
    }

    private static String createToken(String phoneNumber, Long expiredMs)  {
        return Jwts.builder()
                .subject(phoneNumber)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
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

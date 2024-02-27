package com.example.libraryreservation.common.jwt;

import com.example.libraryreservation.common.enums.JwtErrorEnum;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import com.example.libraryreservation.common.model.UserModel;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@AllArgsConstructor
@Slf4j
public class JwtUtil {
    private static final Long accessExpiredMs = (long) (1000 * 60 * 30);
    private static final Long refreshExpiredMs = (long) (1000 * 60 * 60 * 3);

    public static String generateToken(UserModel userModel, String secretKey) {
        return createAccessToken(userModel.getPhoneNumber(), secretKey);
    }

    private static String createAccessToken(String phoneNumber, String secretKey)  {
        return Jwts.builder()
                .subject(phoneNumber)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessExpiredMs))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                .compact();
    }

    public static String createRefreshToken(String secretKey) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshExpiredMs))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                .compact();
    }

    public static boolean isExpired(String token, String secretKey) {
        try{
            return Jwts
                    .parser()
                    .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration()
                    .before(new Date());
        }catch (SignatureException e) {
            throw new JwtException(JwtErrorEnum.WRONG_TYPE_TOKEN.getMsg());
        } catch (MalformedJwtException e) {
            throw new JwtException(JwtErrorEnum.UNSUPPORTED_TOKEN.getMsg());
        } catch (ExpiredJwtException e) {
            throw new JwtException(JwtErrorEnum.EXPIRED_TOKEN.getMsg());
        } catch (IllegalArgumentException e) {
            throw new JwtException(JwtErrorEnum.UNKNOWN_ERROR.getMsg());
        }
    }

    public static String getSubject(String token, String secretKey) {
            return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();

    }
}

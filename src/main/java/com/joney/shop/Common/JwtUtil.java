package com.joney.shop.Common;

import com.joney.shop.Service.CustomUser;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;


@Component
public class JwtUtil {

    static final SecretKey key =
            Keys.hmacShaKeyFor(Decoders.BASE64.decode(
                    "jwtpassword123jwtpassword123jwtpassword123jwtpassword123jwtpassword"
            ));
//public static String createToken(Authentication auth){
//    var user = (CustomUser) auth.getPrincipal();
//    var authorities = auth.getAuthorities().stream().map(a -> a.getAuthority())
//            .collect(Collectors.joining(","));
//
//    String jwt = Jwts.builder()
//            .claim("username", user.getUsername())
//            .claim("displayName", user.displayName)
//            .claim("authorities", authorities)
//            .issuedAt(new Date(System.currentTimeMillis()))
//            .expiration(new Date(System.currentTimeMillis() + 1000)) //유효기간 10초 //ms단위로
//            .signWith(key)
//            .compact();
////    System.out.println("displayName: " + user.displayName);
//
//    return jwt;
//}
//
//    public static String createRefreshToken(CustomUser user) {
//        return Jwts.builder()
//                .claim("username", user.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7일
//                .signWith(key)
//                .compact();
//    }

    public static String createAccessToken(CustomUser user) {
        return Jwts.builder()
                .claim("username", user.getUsername())
                .claim("displayName", user.getDisplayName())
                .claim("id", user.getId())
                .claim("authorities", user.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .signWith(key)
                .compact();
    }

    public static String createRefreshToken(CustomUser user) {
        return Jwts.builder()
                .claim("username", user.getUsername())
                .claim("id", user.getId()) // ID 값을 추가
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 days expiration
                .signWith(key)
                .compact();
    }


    // JWT 까주는 함수
    public static Claims extractToken(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload();
        return claims;
    }
}
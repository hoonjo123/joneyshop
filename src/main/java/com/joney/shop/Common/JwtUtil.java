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
public static String createToken(Authentication auth){
    var user = (CustomUser) auth.getPrincipal();
    var authorities = auth.getAuthorities().stream().map(a -> a.getAuthority())
            .collect(Collectors.joining(","));

    String jwt = Jwts.builder()
            .claim("username", user.getUsername())
            .claim("displayName", user.displayName)
            .claim("authorities", authorities)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 100000)) //유효기간 10초 //ms단위로
            .signWith(key)
            .compact();
    System.out.println("displayName: " + user.displayName);

    return jwt;
}

    // JWT 까주는 함수
    public static Claims extractToken(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload();
        return claims;
    }
}
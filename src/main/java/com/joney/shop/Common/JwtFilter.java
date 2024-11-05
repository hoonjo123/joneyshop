package com.joney.shop.Common;

import com.joney.shop.Service.CustomUser;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;


public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
//        System.out.println("필터실행됨");
//request에는 수많은 정보들이 있다. ip, os정보 등등 이를 통해 검렬가능


        Cookie[] cookies = request.getCookies();

        //null이라면 통과시켜주세욤
        if (cookies == null) {
            filterChain.doFilter(request, response);
            return;
        }


        //쿠키값이 항상 jwt가 아닐 수 있으므로 for문으로 쿠키안에 잇는 jwt값을 출력해보자.

        var jwtCookie = Arrays.stream(cookies)
                .filter(cookie -> "jwt".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        if (jwtCookie == null || jwtCookie.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        Claims claim;
        try {
            claim = JwtUtil.extractToken(jwtCookie);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        // 클레임에서 authorities 추출 및 null 체크
        String authoritiesClaim = claim.get("authorities") != null ? claim.get("authorities").toString() : "";
        if (authoritiesClaim.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        var authorities = Arrays.stream(authoritiesClaim.split(","))
                .map(a -> (GrantedAuthority) new SimpleGrantedAuthority(a))
                .toList();

        // 클레임에서 username과 displayname 추출 및 null 체크
// 클레임에서 username과 displayname 추출 및 null 체크
        String username = claim.get("username") != null ? claim.get("username").toString() : "anonymous";
        String displayName = claim.get("displayname") != null ? claim.get("displayname").toString() : "anonymous";
        // 수정된 부분: ID 값을 추출할 때 Number로 캐스팅 후 Long으로 변환
        Long id = null;
        try {
            id = claim.get("id") != null ? ((Number) claim.get("id")).longValue() : null;
        } catch (ClassCastException e) {
            throw new RuntimeException("Invalid ID format in JWT token", e);
        }


        System.out.println("Decoded displayname: " + displayName);

        // CustomUser 객체 생성
        var customUser = new CustomUser(
                username,
                "none",
                authorities,
                id
        );
        customUser.setDisplayName(displayName);
//        customUser.setDisplayName(claim.get("displayName").toString());

        var authToken = new UsernamePasswordAuthenticationToken(
                customUser,
                null,
                authorities
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
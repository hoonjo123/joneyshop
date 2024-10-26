package com.joney.shop.Controller;

import com.joney.shop.Common.JwtUtil;
import com.joney.shop.Domain.Member;
import com.joney.shop.Domain.RefreshToken;
import com.joney.shop.Dto.MemberDto;
import com.joney.shop.Repository.MemberRepository;
import com.joney.shop.Repository.RefreshTokenRepository;
import com.joney.shop.Service.CustomUser;
import com.joney.shop.Service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.*;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;


    @GetMapping("/members")
    String memberList(Model model) {
        List<Member> result2 = memberRepository.findAll();
        model.addAttribute("members", result2);
        return "members.html";
    }

    @GetMapping("/members/detail/{id}")
    String memberDetail(@PathVariable Long id, Model model) {
        Optional<Member> memberDetail = memberRepository.findById(id);

        if (memberDetail.isPresent()) {
            model.addAttribute("members", memberDetail.get());
            return "memberDetail.html";
        } else {
            throw new RuntimeException("해당 멤버를 찾을 수 없습니다.");
        }
    }

    @GetMapping("/test2")
    String testHasing() {
        var result = new BCryptPasswordEncoder().encode("변환해주세요");
        System.out.println(result);
        return "redirect:/list";
    }

    @GetMapping("/register")
    String joinMember() {
        return "register.html";
    }

    @PostMapping("/member")
    String joinMember(@RequestParam String username,
                      @RequestParam String password,
                      @RequestParam String displayname) {
        Member member = new Member();

        member.setUsername(username);
        var hash = passwordEncoder.encode(password);

        member.setPassword(hash);
        member.setDisplayName(displayname);
        memberRepository.save(member);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        var result = memberRepository.findByUsername("jo");
//        System.out.println(result.get().getDisplayName());
        return "login.html";
    }

    @GetMapping("/mypage")
    String mypage(Authentication auth) {
//        System.out.println(auth.getName());
//        System.out.println(auth);
//        //로그인여부
//        System.out.println(auth.isAuthenticated());
        System.out.println(auth.getAuthorities().contains(new SimpleGrantedAuthority("일반유저")));
        CustomUser result = (CustomUser) auth.getPrincipal();
        System.out.println(result.displayName);
        return "mypage.html";
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public MemberDto getUser(@PathVariable Long id) {
        var a = memberRepository.findById(id);
        if (a.isEmpty()) {
            throw new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
        }
        var result = a.get();
//
//        var map = new HashMap<>();
//        map.put()

        var data = new MemberDto(result.getUsername(), result.getDisplayName(), result.getId());
        return data;
    }

    @PostMapping("/login/jwt")
    @ResponseBody
    public String loginJWT(@RequestBody Map<String, String> data,
                           HttpServletResponse response) {

        var authToken = new UsernamePasswordAuthenticationToken(
                data.get("username"), data.get("password")
        );

        var auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        CustomUser user = (CustomUser) auth.getPrincipal();

        Member member = memberRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));

        // Create Access Token
        String accessToken = JwtUtil.createAccessToken(user);

        // Create Refresh Token
        String refreshToken = JwtUtil.createRefreshToken(user);

        // Save Refresh Token in the database
        RefreshToken tokenEntity = new RefreshToken();
        tokenEntity.setToken(refreshToken);
        tokenEntity.setMember(member);
        tokenEntity.setExpiryDate(LocalDateTime.now().plusDays(7)); // Example of 7 days expiration
        refreshTokenRepository.save(tokenEntity);

        // Add tokens to cookies or response headers
        Cookie jwtCookie = new Cookie("jwt", accessToken);
        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");

        response.addCookie(jwtCookie);
        response.addCookie(refreshCookie);

        return accessToken;
    }

    @GetMapping("/mypage/jwt")
    @ResponseBody
    String mypageJWT(Authentication auth) {

        var user = (CustomUser) auth.getPrincipal();
        System.out.println(user);
        System.out.println(user.displayName);
        System.out.println(user.getAuthorities());

        //일일이 jwt적어주는게 귀찮은뎅
        // filter와 interceptor를 사용해보자


        return "mypage 데이터 출력";
    }

    @PostMapping("/token/refresh")
    @ResponseBody
    public String refreshToken(HttpServletRequest request, HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new RuntimeException("리프레시토큰을 찾을 수 없습니다.");
        }

        RefreshToken tokenEntity = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("올바르지 않은 토큰값입니다."));

        // Check if refresh token has expired
        if (tokenEntity.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("리프레시 토큰이 만료되었습니다.");
        }

        // Generate new Access Token
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String newAccessToken = JwtUtil.createAccessToken(user);

        // Optionally, update refresh token and re-save it
        return newAccessToken;
    }


    class MemberDto {
        public String username;
        public String displayName;
        public Long id;

        MemberDto(String a, String b) {
            this.username = a;
            this.displayName = b;
        }

        MemberDto(String a, String b, Long c) {
            this.username = a;
            this.displayName = b;
            this.id = c;
        }
    }
}





package com.joney.shop.Controller;

import com.joney.shop.Domain.Member;
import com.joney.shop.Repository.MemberRepository;
import com.joney.shop.Service.CustomUser;
import com.joney.shop.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/members")
    String memberList(Model model) {
        List<Member> result2 = memberRepository.findAll();
        model.addAttribute("members", result2);
        return "members.html";
    }

    @GetMapping("/members/detail/{id}")
    String memberDetail(@PathVariable Long id, Model model) {
        Optional<Member> memberDetail = memberRepository.findById(id);

        if (memberDetail.isPresent()){
            model.addAttribute("members", memberDetail.get());
            return "memberDetail.html";
        }else{
            throw new RuntimeException("해당 멤버를 찾을 수 없습니다.");
        }
    }

    @GetMapping("/test2")
    String testHasing(){
        var result = new BCryptPasswordEncoder().encode("변환해주세요");
        System.out.println(result);
        return "redirect:/list";
    }

    @GetMapping("/register")
    String joinMember(){
        return "register.html";
    }

    @PostMapping("/member")
    String joinMember(String username, String password, String displayname){
        Member member = new Member();

        member.setUsername(username);
        var hash = passwordEncoder.encode(password);

        member.setPassword(hash);
        member.setDisplayName(displayname);
        memberRepository.save(member);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        var result = memberRepository.findByUsername("jo");
//        System.out.println(result.get().getDisplayName());
        return "login.html";
    }

    @GetMapping("/mypage")
    String mypage(Authentication auth){
//        System.out.println(auth.getName());
//        System.out.println(auth);
//        //로그인여부
//        System.out.println(auth.isAuthenticated());
        System.out.println(auth.getAuthorities().contains(new SimpleGrantedAuthority("일반유저")));
        CustomUser result = (CustomUser)auth.getPrincipal();
        System.out.println(result.displayName);
        return "mypage.html";
    }

    @GetMapping("/user/1")
    @ResponseBody
    public Data getUser(){
        var a = memberRepository.findById(1L);
        var result = a.get();
//
//        var map = new HashMap<>();
//        map.put()

        var data = new Data();
        data.username = result.getUsername();
        data.displayName = result.getDisplayName();

        return data;
    }
}
class Data{
    public String username;
    public String displayName;
}

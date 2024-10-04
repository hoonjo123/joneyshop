package com.joney.shop.Controller;

import com.joney.shop.Domain.Member;
import com.joney.shop.Repository.MemberRepository;
import com.joney.shop.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

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

    @GetMapping("/join")
    String joinMember(){
        return "join.html";
    }

    @PostMapping("/join")
    String joinMember(@ModelAttribute Member member){
        memberService.saveMember(member);
        return "join.html";
    }
}

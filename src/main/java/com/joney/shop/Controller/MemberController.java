package com.joney.shop.Controller;

import com.joney.shop.Domain.Member;
import com.joney.shop.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    @GetMapping("/members")
    String memberList(Model model) {
        List<Member> result2 = memberRepository.findAll();
        model.addAttribute("members", result2);
        return "members.html";
    }
}

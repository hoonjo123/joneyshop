package com.joney.shop.Controller;

import com.joney.shop.Domain.Member;
import com.joney.shop.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;
import java.util.Optional;


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
}

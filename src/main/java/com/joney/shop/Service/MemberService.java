package com.joney.shop.Service;

import com.joney.shop.Domain.Member;
import com.joney.shop.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void saveMember(@ModelAttribute Member member) {
        memberRepository.save(member);
    }
}

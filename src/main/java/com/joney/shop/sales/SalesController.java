package com.joney.shop.sales;

import com.joney.shop.Domain.Member;
import com.joney.shop.Repository.MemberRepository;
import com.joney.shop.Repository.SalesRepository;
import com.joney.shop.Service.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SalesController {

    private final SalesRepository salesRepository;
    private final MemberRepository memberRepository;
    @PostMapping("/order")
    String OrderItems(@RequestParam String title,
                      Integer price,
                      Integer count,
                      Authentication auth){
        Sales sales = new Sales();
        sales.setCount(count);
        sales.setPrice(price);
        sales.setItemName(title);

        CustomUser customUser = (CustomUser) auth.getPrincipal();
//        System.out.println(user.id);
        Member member = memberRepository.findById(customUser.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")
        );
        sales.setMember(member);

        salesRepository.save(sales);

        return "redirect:list?success=true";
    }

    @GetMapping("/order/all")
    String getOrderAll(Model model){
        List<Sales> result = salesRepository.findAll();
//        System.out.println(result.get());
        model.addAttribute("orders",result);
        return "OrderList.html";
    }
}

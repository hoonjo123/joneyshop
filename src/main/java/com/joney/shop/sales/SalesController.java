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
import java.util.stream.Collectors;

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
        System.out.println("User ID: " + customUser.getId());
        Member member = memberRepository.findById(customUser.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")
        );
        sales.setMember(member);

        salesRepository.save(sales);

        return "redirect:list?success=true";
    }


    //ManyToOne을 쓰면 편리하긴 하지만 N+1의 문제가 발생할 수 있다.
    //JOIN문법으로 해결해보자.
    //JPQL문법으로 행만큼 실행되던 sql을 단 한번으로 해결했음.

    //ManyToOne을 사용하면 패스워드도 함께 가져오는데..? RestAPI로 보낸다면 참사가 일어날 것 같음
    //Map이나 Dto를 만들어서 원하는 정보만 보내보자.
    @GetMapping("/order/all")
    String getOrderAll(Model model){
        List<Sales> result = salesRepository.customFindAll();
//        System.out.println(result.get());
//        var salesDto = new SalesDto();
//        salesDto.itemName = result.get(0).getItemName();

        List<SalesDto> salesDtoList = result.stream()
                .map(sales -> new SalesDto(sales.getItemName(), sales.getPrice(), sales.getMember().getUsername()))
                .collect(Collectors.toList());

        model.addAttribute("orders",result);
        return "OrderList.html";
    }

    public class SalesDto{
        String itemName;
        Integer price;
        String username;

        public SalesDto(String itemName, Integer price, String username){
            this.itemName = itemName;
            this.price = price;
            this.username = username;
        }
    }
}

package com.joney.shop.sales;

import com.joney.shop.Repository.SalesRepository;
import com.joney.shop.Service.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SalesController {

    private final SalesRepository salesRepository;
    @PostMapping("/order")
    String OrderItems(@RequestParam String title, Integer price, Integer count, Authentication auth){
        Sales sales = new Sales();
        sales.setCount(count);
        sales.setPrice(price);
        sales.setItemName(title);

        CustomUser user = (CustomUser) auth.getPrincipal();
//        System.out.println(user.id);
//        sales.setMemberId(user.id);

        salesRepository.save(sales);

        return "list.html";
    }

    @GetMapping("/order/all")
    String getOrderAll(){
        List<Sales> result = salesRepository.findAll();
        System.out.println(result.get(1));
        return "list.html";
    }
}

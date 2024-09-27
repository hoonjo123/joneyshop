package com.joney.shop.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
    @GetMapping("/list")
    String list(Model model){
//        model.addAttribute("전달할데이터이름","데이터");
        model.addAttribute("name","훈");
        return "list.html";
    }
}

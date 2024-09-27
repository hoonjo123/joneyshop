package com.joney.shop.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
public class HomeController {
    @GetMapping("/")
    String hello(){
        return "index.html";
    }

    @GetMapping("/about")
    @ResponseBody
    String about() {
        return "about bitch";
    }
    @GetMapping("/date")
    @ResponseBody
    String today() {
        return LocalDateTime.now().toString();
    }

}

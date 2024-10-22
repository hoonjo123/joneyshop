package com.joney.shop.Controller;

import com.joney.shop.Domain.Comment;
import com.joney.shop.Repository.CommentRepository;
import com.joney.shop.Service.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;


    @PostMapping("/comment")
    public String postComment(@RequestParam String content,
                       @RequestParam Long parent,
                       Authentication auth){

        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";  // 인증되지 않은 사용자는 로그인 페이지로 이동
        }

        CustomUser user = (CustomUser) auth.getPrincipal();




        var data = new Comment();
        data.setContent(content);
        data.setUsername(user.getUsername());
        data.setParentId(parent);
//        data.setParentId();


        System.out.println(data);
        commentRepository.save(data);
        return "redirect:/detail/" + parent;
    }

    @GetMapping("/comment-detail")
    public String detailComment(@RequestParam Long id){

        var comments = new Comment();
        return "comments";
    }
}

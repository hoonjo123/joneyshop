package com.joney.shop.Controller;

import com.joney.shop.Domain.Board;
import com.joney.shop.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardRepository boardRepository;

    @GetMapping("/board")
    String list(Model model) {
        List<Board> result1 = boardRepository.findAll();
//        System.out.println("a" + result1);
        model.addAttribute("boards", result1);
        return "board.html";
    }
}

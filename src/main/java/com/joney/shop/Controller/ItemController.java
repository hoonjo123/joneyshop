package com.joney.shop.Controller;

import com.joney.shop.Domain.Item;
import com.joney.shop.Repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;



@Controller
@RequiredArgsConstructor
public class ItemController {
    //    @GetMapping("/list")
//    String list(Model model){
////        model.addAttribute("전달할데이터이름","데이터");
//        model.addAttribute("name","훈");
//        return "list.html";

    //변수를 사용하기 위해 requiredargsconstr..를 붙여줌
    private final ItemRepository itemRepository;

    //콘스트럭터 : 롬복을 쓰기 싫다면 ? generate => 콘스트럭터 사용하기
//    @Autowired
//    public ItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    //JPA를 사용해서 데이터를 꺼내오자

    @GetMapping("/list")
    String list(Model model) {
        List<Item> result = itemRepository.findAll(); //item테이블의 모든 정보, list자료형
        model.addAttribute("items", result);
        var a = new Item();
        System.out.println(a.toString());

        return "list.html";
    }

    @GetMapping("/write")
    String write(){
        return "write.html";
    }

    @PostMapping("/add")
//    String addPost(@RequestParam String title,@RequestParam Integer price)
    String addPost(@RequestParam Map<String, String> formData){
        //유저가 보낸 데이터를 어떤식으로 출력할 수 있을까?
        //parameter를 통해 유저가 어떤 이름으로 보낸지 알 수 있다.
//        //맵자료형
//        HashMap<String,Object> test = new HashMap<>();
//        test.put("name","kim");
//        test.put("age","20");
//        System.out.println(test);
//        System.out.println(formData);

        String title = formData.get("title");
        Integer price = Integer.parseInt(formData.get("price"));

        Item newItem = new Item();
        newItem.setTitle(title);
        newItem.setPrice(price);

        itemRepository.save(newItem);
        return "redirect:/list";
    }


    //        System.out.println(result.get(0).price);
//        System.out.println(result.get(0).title);

    //여러 데이터를 한 변수에 넣으려면? arraylist
//        ArrayList<Integer> a = new ArrayList<>();
//        ArrayList<String> a = new ArrayList<>();
//        ArrayList<Object> a = new ArrayList<>();
//        List<Object> a = new ArrayList<>();

//        a.add(30);
//        a.add(40);
//        System.out.println(a.get(0));
//        System.out.println(a.get(1));
}


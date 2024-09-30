package com.joney.shop.Controller;

import com.joney.shop.Domain.Item;
import com.joney.shop.Repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
//    String addPost(@RequestParam Map<String, String> formData)
    String addPost(@ModelAttribute Item item){
        //유저가 보낸 데이터를 어떤식으로 출력할 수 있을까?
        //parameter를 통해 유저가 어떤 이름으로 보낸지 알 수 있다.
//        //맵자료형
//        HashMap<String,Object> test = new HashMap<>();
//        test.put("name","kim");
//        test.put("age","20");
//        System.out.println(test);
//        System.out.println(formData);

//        String title = formData.get("title");
//        Integer price = Integer.parseInt(formData.get("price"));
//
//        Item item = new Item();
//        item.setTitle(title);
//        item.setPrice(price);
//        itemRepository.save(item);

        //조금 더 쉽게 변환해보자. @ModelAttribute Item item =>

//        System.out.println(item);
        itemRepository.save(item);

        return "redirect:/list";
    }

    //상품 상세페이지
    //URL 파라미터 문법을 사용해서 비슷한 url의 api를 여러개 만들필요 없이 생성해보자
    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model){
        System.out.println("받은아이디 :"+ id);
        Optional<Item> result = itemRepository.findById(id);
        //Optional 기입해야하는 이유 : 만든 사람이 그렇게 출력하도록 만들어짐, 비어있을수도, item일수도 있다
        //만약 100L라고 한다면? 근데 없다면 null이다.

        //Optional출력할때는 .get을 사용
//        System.out.println(result.get()); //result 비어있으면 어쩔것임


        //꼭 체크후 꺼내보자.
        if(result.isPresent()){
//            Item item = result.get();
            System.out.println(result.get());
            model.addAttribute("items",result.get());
        }else{
            model.addAttribute("에러","아이템을 찾을 수 없습니다.");
            return "list.html";
        }

        return "detail.html";
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


package com.joney.shop.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;

@Entity
@ToString
@Getter
@Setter
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;


    //20억까지만 저장이 가능한 Integer
    //좀 더 큰 숫자는 Long으로 사용 900경까지 사용가능
    //모든 곳에서 불러오기위해 public으로 우선사용. private으로 붙여도 되는데 getter,setter가 필요함

    @Column
    private Integer price;

//    //가져오고
//    public String getTitle() {
//        return title;
//    }
//
//    //수정하고
//    public void setTitle(String title) {
//    "if 255이하라면 제목을 수정할수있도록 해주세요"
//    라는 문구를 추가해줘야 setter함수의 의마가 있음
//        this.title = title;
//    }
    //object의 변수들 한번에 출력하는 법
    //@ToString 롬복
}

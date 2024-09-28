package com.joney.shop.Domain;

import jakarta.persistence.*;
import lombok.ToString;
import org.springframework.stereotype.Controller;

@Entity
@ToString
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String title;

    //20억까지만 저장이 가능한 Integer
    //좀 더 큰 숫자는 Long으로 사용 900경까지 사용가능
    //모든 곳에서 불러오기위해 public으로 우선사용. private으로 붙여도 되는데 getter,setter가 필요함

    @Column
    public Integer price;

    //object의 변수들 한번에 출력하는 법
    //@ToString 롬복

}

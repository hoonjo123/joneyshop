package com.joney.shop.Domain;

import com.joney.shop.sales.Sales;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

//    @Column(unique = true)
    private String username;

    private String displayName;

    private String password;

    //이렇게 작성하면 stackoverflow가 발생
    //exclude를 사용해야함
    @ToString.Exclude
    @OneToMany(mappedBy = "member")
    private List<Sales> sales = new ArrayList<>();


}

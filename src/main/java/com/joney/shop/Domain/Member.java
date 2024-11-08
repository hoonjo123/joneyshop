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
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role = MemberRole.USER;

    @Column(nullable = false, length = 200)
    private String zoneCode;

    @Column(nullable = false)
    private String roadAddress;

    @Column(nullable = false)
    private String detailAddress;

    //이렇게 작성하면 stackoverflow가 발생
    //exclude를 사용해야함
    @ToString.Exclude
    @OneToMany(mappedBy = "member")
    private List<Sales> sales = new ArrayList<>();


}

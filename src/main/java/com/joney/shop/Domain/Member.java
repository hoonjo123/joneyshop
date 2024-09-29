package com.joney.shop.Domain;

import jakarta.persistence.*;
import lombok.ToString;

@Entity
@ToString
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    public Integer age;
}

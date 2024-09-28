package com.joney.shop.Domain;
import jakarta.persistence.*;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@ToString
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column()
    public String title;

    @Column()
    public String data;
}

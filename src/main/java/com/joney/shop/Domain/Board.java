package com.joney.shop.Domain;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "게시글 제목",nullable = false)
    String title;

    @Column(name = "작성 시간")
    LocalDateTime data;
}

package com.joney.shop.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime expiryDate;
}

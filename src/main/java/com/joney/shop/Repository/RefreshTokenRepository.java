package com.joney.shop.Repository;

import com.joney.shop.Domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByMemberId(Long memberId);
    List<RefreshToken> findByMemberId(Long memberId);

}

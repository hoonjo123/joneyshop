package com.joney.shop.Repository;

import com.joney.shop.Domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;



public interface MemberRepository extends JpaRepository<Member, Long> {
}

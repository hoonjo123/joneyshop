package com.joney.shop.Repository;

import com.joney.shop.Domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}

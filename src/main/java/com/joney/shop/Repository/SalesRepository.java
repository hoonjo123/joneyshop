package com.joney.shop.Repository;

import com.joney.shop.sales.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Long> {

    //join문법을 쌩으로 작성하기보다는 JPQL로 작성하면 더 수월하게 작업가능
    @Query(value = "SELECT s FROM Sales s JOIN FETCH s.member")
    List<Sales> customFindAll();
}

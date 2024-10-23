package com.joney.shop.Repository;

import com.joney.shop.sales.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Long> {
}

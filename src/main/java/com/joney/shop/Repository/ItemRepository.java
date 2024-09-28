package com.joney.shop.Repository;

import com.joney.shop.Domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

//interface를 만들어도 같은 이름을 가진 클래스를 만들어줌
//db입출력을 도와주는 함수들이 되게 많이 들어잇음
public interface ItemRepository extends JpaRepository<Item, Long> {

}
package com.joney.shop.Service;

import com.joney.shop.Domain.Item;


import com.joney.shop.Repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(@ModelAttribute Item item){
        itemRepository.save(item);
    }
}

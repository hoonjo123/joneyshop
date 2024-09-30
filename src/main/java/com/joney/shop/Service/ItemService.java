package com.joney.shop.Service;

import com.joney.shop.Domain.Item;


import com.joney.shop.Repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(@ModelAttribute Item item){
        itemRepository.save(item);
    }

    public void editItem(String title, Integer price, Long id){

        Item item = new Item();
        item.setId(id);
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);
    }
}

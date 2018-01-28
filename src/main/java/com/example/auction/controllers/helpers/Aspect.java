package com.example.auction.controllers.helpers;

import com.example.auction.models.Item;
import com.example.auction.repositories.ItemRepository;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@org.aspectj.lang.annotation.Aspect
@Component
public class Aspect {

    @Autowired
    private ItemRepository itemRepository;

    @Before("execution(* com.example.auction.controllers.ItemController.*(..))")
    public void updateBids(){
        List<Item> items = itemRepository.findItemsByStatusAndEndDateLessThanOrderByEndDateAsc(Item.Statuses.Aktywna.name(), new Date());
        for (Item item: items) {
            item.setStatus(Item.Statuses.Zakonczona.name());
        }
        itemRepository.save(items);
    }
}

package com.example.auction.services;

import com.example.auction.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface ItemService extends Service<Item>{

    Page<Item> getAllOnSale(Pageable pageable, Item.Statuses status, Date endDate);
}

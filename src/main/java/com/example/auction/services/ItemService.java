package com.example.auction.services;

import com.example.auction.controllers.helpers.SearchFilter;
import com.example.auction.models.Item;
import com.example.auction.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ItemService extends Service<Item>{

//    Page<Item> getAllOnSale(SearchFilter searchFilter, Pageable pageable, Item.Statuses status, Date endDate);
    List<Item> getAllOnSale(SearchFilter searchFilter, Item.Statuses status, Date endDate);

    Page<Item> getAllByUser(Pageable pageable, User user);
    List<Item> getAllByUser(User user);
}

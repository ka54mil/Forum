package com.example.auction.services;

import com.example.auction.models.Item;
import com.example.auction.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService
{
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Item getById(Long id) {
        return null;
    }

    @Override
    public Page<Item> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public Item save(Item item) {
        return null;
    }
}

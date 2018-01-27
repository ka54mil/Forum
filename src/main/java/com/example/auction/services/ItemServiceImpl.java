package com.example.auction.services;

import com.example.auction.models.Item;
import com.example.auction.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService
{
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Item getById(Long id) {
        return itemRepository.findOne(id);
    }

    @Override
    public Page<Item> getAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    @Override
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void delete(Long id) {
        itemRepository.delete(id);
    }

    @Override
    public Page<Item> getAllOnSale(Pageable pageable, Item.Statuses status, Date endDate) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return itemRepository.findAllByStatusAndEndDateGreaterThanEqualOrderByEndDateAsc(pageable, status.name(), df.format(endDate));
    }
}

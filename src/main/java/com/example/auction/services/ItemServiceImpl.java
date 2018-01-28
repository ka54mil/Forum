package com.example.auction.services;

import com.example.auction.controllers.helpers.SearchFilter;
import com.example.auction.models.Item;
import com.example.auction.repositories.ItemRepository;
import com.example.auction.repositories.criteria.ItemsSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
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
        if(item.getId() != null){
            item.setCreated(itemRepository.findOne(item.getId()).getCreated());
        }
        return itemRepository.save(item);
    }

    @Override
    public void delete(Long id) {
        itemRepository.delete(id);
    }

    @Override
    public Page<Item> getAllOnSale(SearchFilter searchFilter, Pageable pageable, Item.Statuses status, Date endDate) {
        return itemRepository.findAllByStatusAndEndDateGreaterThanEqualOrderByEndDateAsc(
                Specification.where(ItemsSpecifications.findByName(searchFilter.getName())
                                    .and(ItemsSpecifications.findByPriceRange(searchFilter.getMinPrice(), searchFilter.getMinPrice()))
                ),
                pageable,
                status.name(),
                endDate);
    }
}

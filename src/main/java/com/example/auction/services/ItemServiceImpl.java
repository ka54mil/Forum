package com.example.auction.services;

import com.example.auction.controllers.helpers.SearchFilter;
import com.example.auction.models.Category;
import com.example.auction.models.Item;
import com.example.auction.models.User;
import com.example.auction.repositories.ItemRepository;
import com.example.auction.repositories.criteria.ItemsSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public List<Item> getAllOnSale(SearchFilter searchFilter, Item.Statuses status, Date endDate) {
//        Specification.where(ItemsSpecifications.findByName(searchFilter.getName())
//                .and(ItemsSpecifications.findByPriceRange(searchFilter.getMinPrice(), searchFilter.getMinPrice()))
//        ),
//        Set<Category> categories = null;
//        if(searchFilter.getCategories() != null && !searchFilter.getCategories().isEmpty()){
//            categories = new HashSet<>(searchFilter.getCategories());
//        }
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return itemRepository.findAllByStatusAndEndDateGreaterThanEqualOrderByEndDateAsc(
                searchFilter.getNameLIKE(),
                searchFilter.getMinPrice(),
                searchFilter.getMaxPrice(),
//                pageable,
                status.name());
    }

    @Override
    @Transactional
    public Page<Item> getAllByUser(Pageable pageable, User user) {
        return itemRepository.findItemsByOwner(pageable,user);
    }

    @Override
    public List<Item> getAllByUser(User user) {
        return itemRepository.findItemsByOwner(user);
    }
}

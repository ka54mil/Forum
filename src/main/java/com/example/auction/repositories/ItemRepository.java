package com.example.auction.repositories;

import com.example.auction.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findByStatus(String status);
    Page<Item> findAllByStatusAndEndDateGreaterThanEqualOrderByEndDateAsc(Pageable pageable, String status, Date endDate);
    List<Item> findItemsByStatusAndEndDateLessThanOrderByEndDateAsc(String status, Date endDate);
}

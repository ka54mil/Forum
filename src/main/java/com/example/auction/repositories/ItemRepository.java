package com.example.auction.repositories;

import com.example.auction.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findByStatus(String status);
    Page<Item> findAllByStatusAndEndDateGreaterThanEqualOrderByEndDateAsc(Pageable pageable, String status, String endDate);
}
